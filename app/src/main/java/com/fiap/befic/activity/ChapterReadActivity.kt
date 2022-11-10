package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.data.Capitulo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChapterReadActivity : AppCompatActivity() {

    var userId = 0L;
    var storyId = 0L;
    var storyName = "";
    var chapterId = 0L;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_read)

        userId = intent.getSerializableExtra("USER_ID") as Long
        storyId = intent.getSerializableExtra("STORY_ID") as Long
        storyName = intent.getSerializableExtra("STORY_NAME") as String
        chapterId = intent.getSerializableExtra("CHAPTER_ID") as Long

        val callChapter =
            BeficBackendFactory().capituloBeficBackendService().findById(chapterId);

        getChapterInfo(callChapter, this);
    }

    fun getChapterInfo(callback: Call<Capitulo>, context: Context) {
        callback.enqueue(object : Callback<Capitulo> {
            override fun onFailure(call: Call<Capitulo>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Capitulo>,
                response: Response<Capitulo>
            ) {
                response.body()?.let {
                    val storyChapter =
                        findViewById<View>(R.id.txv_nome_historia_numero_cap) as TextView
                    storyChapter.text =
                        String.format("% - capítulo %", storyName, it.numero)

                    val notas =
                        findViewById<View>(R.id.txv_notas) as TextView
                    notas.text = it.notasIniciais

//                    val historia =
//                        findViewById<View>(R.id.txv_historia) as TextView
//                    historia.text = it.conteudo

                    val notasFinais =
                        findViewById<View>(R.id.txv_notas_finais) as TextView
                    notasFinais.text = it.notasFinais

                }
            }
        })
    }
}