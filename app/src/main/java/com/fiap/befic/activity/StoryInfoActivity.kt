package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.data.entity.Capitulo
import com.fiap.befic.data.entity.Historia
import com.fiap.befic.data.entity.Login
import com.fiap.befic.utils.ShowViewUtils
import com.fiap.befic.utils.UserInfoUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryInfoActivity : AppCompatActivity() {


    lateinit var context: Context
    var loggedUserId = 0L;
    var userId = 0L;
    var storyId = 0L;
    var storyName = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_info)

        loggedUserId = UserInfoUtils.userId
        userId = intent.getSerializableExtra("USER_ID") as Long
        storyId = intent.getSerializableExtra("STORY_ID") as Long
        context = this

        val callLogin =
            BeficBackendFactory().loginBeficBackendService().findByUsuario(userId);

        val callStory =
            BeficBackendFactory().historiaBeficBackendService().findById(storyId);

        val callChapters =
            BeficBackendFactory().historiaBeficBackendService().getCapitulos(storyId);

        getLoginInfo(callLogin)
        getStoryInfo(callStory);
        getChaptersInfo(callChapters);

        val createChapterButton = findViewById<View>(R.id.btn_criar_capitulo) as Button
        val deleteStoryButton = findViewById<View>(R.id.btn_deletar_historia) as Button
        if (loggedUserId != userId) {
            ShowViewUtils.hide(createChapterButton)
            ShowViewUtils.hide(deleteStoryButton)
            val inflater: LayoutInflater = LayoutInflater.from(this)
        }
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
                    val autor = findViewById<View>(R.id.txv_autor) as TextView
                    autor.text = it.username
                }
            }
        })
    }

    fun getStoryInfo(callback: Call<Historia>) {
        callback.enqueue(object : Callback<Historia> {
            override fun onFailure(call: Call<Historia>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Historia>,
                response: Response<Historia>
            ) {
                response.body()?.let {
                    val storyNameView = findViewById<View>(R.id.txv_nome_historia) as TextView
                    storyName = it.nome
                    storyNameView.text = it.nome

                    val sinopse = findViewById<View>(R.id.txv_sinopse) as TextView
                    sinopse.text = it.sinopse

                    val data_publicacao = findViewById<View>(R.id.txv_data_publicacao) as TextView
                    data_publicacao.text = it.dtPublicacao

                    val data_atualizacao = findViewById<View>(R.id.txv_data_atualizacao) as TextView
                    data_atualizacao.text = it.dtAtualizacao

                }
            }
        })
    }

    fun getChaptersInfo(callback: Call<List<Capitulo>>) {
        callback.enqueue(object : Callback<List<Capitulo>> {
            val chapters = ArrayList<Capitulo>()

            override fun onFailure(call: Call<List<Capitulo>>, t: Throwable) {
                Log.i("erro:", t.message.toString())
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<Capitulo>>,
                response: Response<List<Capitulo>>
            ) {

                response.body()?.forEach {
                    chapters.add(it)
                }

                val chapterList = findViewById<ListView>(R.id.chapterList)

                val arrayadapter = ChapterItemListAdapter(context, chapters)

                chapterList!!.adapter = arrayadapter
                setListViewHeightBasedOnChildren(chapterList)

                chapterList.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                    val i = Intent(context, ChapterReadActivity::class.java)
                    i.putExtra("USER_ID", userId);
                    i.putExtra("STORY_ID", storyId);
                    i.putExtra("STORY_NAME", storyName);
                    i.putExtra("CHAPTER_NUMBER", id);
                    startActivity(i)
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


    fun goToHome(view: View?) {
        val btnHome = findViewById<View>(R.id.home)

        val i = Intent(context, UserProfileActivity::class.java)
        i.putExtra("USER_ID", UserInfoUtils.userId);
        startActivity(i)

    }

    fun createChapter(view: View?) {
        val i = Intent(context, CreateChapterActivity::class.java)
        i.putExtra("STORY_ID", storyId);
        i.putExtra("STORY_NAME", storyName);
        startActivity(i)
    }

    fun deleteStory(view: View?) {
        val builder = AlertDialog.Builder(context);
        builder.setTitle("Deletar");
        builder.setMessage("Tem certeza que deseja deletar a hist??ria?")
            .setCancelable(false)
            .setPositiveButton("Sim") { dialog, id ->
                val callStoryDelete =
                    BeficBackendFactory().historiaBeficBackendService().delete(storyId);
                getDeleteStoryInfo(callStoryDelete)
            }
            .setNegativeButton("N??o") { dialog, id ->
                dialog.dismiss()
            }

        val alert = builder.create()
        alert.show()
    }

    fun getDeleteStoryInfo(callback: Call<Void>) {
        callback.enqueue(object : Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("erro:", t.message.toString())
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                Log.i("sucesso:", "Hist??ria exclu??da com sucesso.")
                Toast.makeText(baseContext, "Hist??ria exclu??da com sucesso.", Toast.LENGTH_SHORT)
                    .show()

                Handler()
                    .postDelayed(
                        Runnable {
                            val i = Intent(context, UserProfileActivity::class.java)
                            i.putExtra("USER_ID", userId);
                            startActivity(i)
                        }, 1500
                    )
            }
        })
    }

}