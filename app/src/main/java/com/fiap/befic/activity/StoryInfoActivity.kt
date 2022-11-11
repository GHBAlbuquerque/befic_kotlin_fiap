package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.data.Capitulo
import com.fiap.befic.data.Historia
import com.fiap.befic.data.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryInfoActivity : AppCompatActivity() {

    var userId = 0L;
    var storyId = 0L;
    var storyName = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_info)

        userId = intent.getSerializableExtra("USER_ID") as Long
        storyId = intent.getSerializableExtra("STORY_ID") as Long

        val callLogin =
            BeficBackendFactory().loginBeficBackendService().findByUsuario(userId);

        val callStory =
            BeficBackendFactory().historiaBeficBackendService().findById(storyId);

        val callChapters =
            BeficBackendFactory().historiaBeficBackendService().getCapitulos(storyId);

        getLoginInfo(callLogin, this)
        getStoryInfo(callStory, this);
        getChaptersInfo(callChapters, this);

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
                    val autor = findViewById<View>(R.id.txv_autor) as TextView
                    autor.text = it.username
                }
            }
        })
    }

    fun getStoryInfo(callback: Call<Historia>, context: Context) {
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

    fun getChaptersInfo(callback: Call<List<Capitulo>>, context: Context) {
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
                    view.setOnClickListener {
                        val i = Intent(context, ChapterReadActivity::class.java)
                        i.putExtra("USER_ID", userId);
                        i.putExtra("STORY_ID", storyId);
                        i.putExtra("STORY_NAME", storyName);
                        i.putExtra("CHAPTER_NUMBER", id);
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