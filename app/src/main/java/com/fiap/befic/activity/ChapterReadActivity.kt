package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.data.entity.Capitulo
import com.fiap.befic.data.entity.CapituloId
import com.fiap.befic.utils.ShowViewUtils
import com.fiap.befic.utils.UserInfoUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChapterReadActivity : AppCompatActivity() {

    lateinit var context: Context
    var userId = 0L;
    var loggedUserId = 0L;
    var storyId = 0L;
    var storyName = "";
    var chapterNumber = 0L;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_read)

        loggedUserId = UserInfoUtils.userId
        userId = intent.getSerializableExtra("USER_ID") as Long
        storyId = intent.getSerializableExtra("STORY_ID") as Long
        storyName = intent.getSerializableExtra("STORY_NAME") as String
        chapterNumber = intent.getSerializableExtra("CHAPTER_NUMBER") as Long
        context = this

        val chapterId = CapituloId(chapterNumber, storyId)

        val callChapter =
            BeficBackendFactory().capituloBeficBackendService().findById(chapterId);

        val deleteChapterButton = findViewById<View>(R.id.btn_deletar_capitulo) as Button
        if (loggedUserId != userId) {
            ShowViewUtils.hide(deleteChapterButton)
            val inflater: LayoutInflater = LayoutInflater.from(this)
        }

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
                    val numero = it.numero.toString();
                    storyChapter.text =
                        String.format("%s - capítulo %s", storyName, numero)

                    val notas =
                        findViewById<View>(R.id.txv_notas) as TextView
                    notas.text = it.notasIniciais

                    if (notas.text.equals("")) {
                        ShowViewUtils.hide(notas)
                        val notasTitulo = findViewById<View>(R.id.notas) as View
                        ShowViewUtils.hide(notasTitulo)
                    }

                    val nomeCapitulo =
                        findViewById<View>(R.id.txv_nome_cap) as TextView
                    nomeCapitulo.text = it.titulo

                    val conteudo =
                        findViewById<View>(R.id.txv_conteudo) as TextView
                    conteudo.text = it.conteudo

                    val notasFinais =
                        findViewById<View>(R.id.txv_notas_finais) as TextView
                    notasFinais.text = it.notasFinais

                    if (notasFinais.text.equals("")) {
                        ShowViewUtils.hide(notasFinais)
                        val notasFinaisTitulo = findViewById<View>(R.id.notas_finais) as View
                        ShowViewUtils.hide(notasFinaisTitulo)
                    }


                }
            }
        })
    }

    fun goToHome(view: View?) {
        val btnHome = findViewById<View>(R.id.home)

        btnHome.setOnClickListener {

            val i = Intent(context, UserProfileActivity::class.java)
            i.putExtra("USER_ID", UserInfoUtils.userId);
            startActivity(i)
        }
    }

    fun deleteChapter(view: View?) {
        val builder = AlertDialog.Builder(context);
        builder.setTitle("Deletar");
        builder.setMessage("Tem certeza que deseja deletar o capítulo?")
            .setCancelable(false)
            .setPositiveButton("Sim") { dialog, id ->
                val chapterId = CapituloId(chapterNumber, storyId)

                val callStoryDelete =
                    BeficBackendFactory().capituloBeficBackendService().delete(chapterId);
                getDeleteChapterInfo(callStoryDelete)
            }
            .setNegativeButton("Não") { dialog, id ->
                dialog.dismiss()
            }

        val alert = builder.create()
        alert.show()
    }

    fun getDeleteChapterInfo(callback: Call<Void>) {
        callback.enqueue(object : Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("erro:", t.message.toString())
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                Log.i("sucesso:", "Capítulo excluído com sucesso.")
                Toast.makeText(baseContext, "Capítulo excluído com sucesso.", Toast.LENGTH_SHORT)
                    .show()

                Handler()
                    .postDelayed(
                        Runnable {
                            val i = Intent(context, StoryInfoActivity::class.java)
                            i.putExtra("USER_ID", userId);
                            i.putExtra("STORY_ID", storyId);
                            startActivity(i)
                        }, 1500
                    )
            }
        })
    }
}