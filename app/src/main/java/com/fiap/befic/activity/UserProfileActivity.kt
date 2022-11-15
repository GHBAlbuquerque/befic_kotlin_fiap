package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.data.entity.Historia
import com.fiap.befic.data.entity.Login
import com.fiap.befic.data.entity.Usuario
import com.fiap.befic.utils.ShowViewUtils
import com.fiap.befic.utils.UserInfoUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserProfileActivity : AppCompatActivity() {

    lateinit var context: Context
    var loggedUserId = 0L;
    var userId = 0L;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        context = this
        loggedUserId = UserInfoUtils.userId
        userId = intent.getSerializableExtra("USER_ID") as Long

        val callLoginInfo = BeficBackendFactory().loginBeficBackendService().findByUsuario(userId);
        val callUserInfo = BeficBackendFactory().usuarioBeficBackendService().findById(userId);
        val callStoriesByUser =
            BeficBackendFactory().historiaBeficBackendService().findByAutor(userId);

        val createStoryButton = findViewById<View>(R.id.btn_criar_historia) as Button
        if (loggedUserId != userId) {
            ShowViewUtils.hide(createStoryButton)
            val inflater: LayoutInflater = LayoutInflater.from(this)
            val view: View = inflater.inflate(R.layout.activity_navigation_bar, null)
        }

        getLoginInfo(callLoginInfo)
        getUserInfo(callUserInfo)
        getStoriesNames(callStoriesByUser);


    }

    fun getLoginInfo(callback: Call<Login>) {
        callback.enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Login>,
                response: Response<Login>
            ) {
                response.body()?.let {
                    val userName = findViewById<View>(R.id.txv_nome) as TextView
                    userName.text = it.username
                }
            }
        })
    }

    fun getUserInfo(callback: Call<Usuario>) {
        callback.enqueue(object : Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.i("erro:", t.message.toString())
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Usuario>,
                response: Response<Usuario>
            ) {
                response.body()?.let {
                    val aboutMe = findViewById<View>(R.id.txv_sobre_mim_text) as TextView
                    aboutMe.text = it.perfil
                }
            }
        })
    }


    fun getStoriesNames(callback: Call<List<Historia>>) {
        val stories = ArrayList<Historia>()

        callback.enqueue(object : Callback<List<Historia>> {
            override fun onFailure(call: Call<List<Historia>>, t: Throwable) {
                Log.i("erro:", t.message.toString())
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<Historia>>,
                response: Response<List<Historia>>
            ) {
                response.body()?.forEach {
                    stories.add(it)
                }

                if (stories.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Usuário não possui histórias!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                val storyList = findViewById<ListView>(R.id.storyList)

                val arrayadapter = StoryItemListAdapter(context, stories)

                storyList!!.adapter = arrayadapter
                setListViewHeightBasedOnChildren(storyList)

                storyList.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
                    view.setOnClickListener {
                        val i = Intent(context, StoryInfoActivity::class.java)
                        i.putExtra("USER_ID", 2L);
                        i.putExtra("STORY_ID", id);
                        startActivity(i)
                    }
                })

            }
        })
    }

    fun setListViewHeightBasedOnChildren(myListView: ListView?) {
        val adapter: ListAdapter = myListView!!.adapter
        var totalHeight = 0
        for (i in 0 until adapter.getCount()) {
            val item: View = adapter.getView(i, null, myListView)
            item.measure(0, 0)
            totalHeight += item.measuredHeight
        }
        val params = myListView.layoutParams
        params.height = totalHeight + myListView.dividerHeight * (adapter.getCount() - 1)
        myListView.layoutParams = params
    }

    fun createStory(view: View?) {
        val btnCreateStory = findViewById<Button>(R.id.btn_criar_historia)


        val i = Intent(context, CreateStoryActivity::class.java)
        i.putExtra("USER_ID", userId);
        startActivity(i)

    }

}