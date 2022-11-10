package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.data.Historia
import com.fiap.befic.data.Login
import com.fiap.befic.data.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserProfileActivity : AppCompatActivity() {

    var userId = 0L;
    var loginId = 0L;


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        userId = intent.getSerializableExtra("USER_ID") as Long

        val callLoginInfo = BeficBackendFactory().loginBeficBackendService().findByUsuario(userId);
        val callUserInfo = BeficBackendFactory().usuarioBeficBackendService().findById(userId);
        val callStoriesByUser =
            BeficBackendFactory().historiaBeficBackendService().findByAutor(userId);

        getLoginInfo(callLoginInfo, this)
        getUserInfo(callUserInfo, this)
        getStoriesNames(callStoriesByUser, this);
    }

    fun getLoginInfo(callback: Call<Login>, context: Context) {
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

    fun getUserInfo(callback: Call<Usuario>, context: Context) {
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


    fun getStoriesNames(callback: Call<List<Historia>>, context: Context) {
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


}