package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.data.dto.CreateChapterDto
import com.fiap.befic.data.entity.Capitulo
import com.fiap.befic.utils.UserInfoUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateChapterActivity : AppCompatActivity() {

    lateinit var context: Context
    var storyName = ""
    var storyId = 0L
    var chapterName = ""
    var content = ""
    var notes = ""
    var endingNotes = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_chapter)

        storyId = intent.getSerializableExtra("STORY_ID") as Long
        storyName = intent.getSerializableExtra("STORY_NAME") as String
        context = this;

        val storyNameView = findViewById<View>(R.id.story_name) as TextView
        storyNameView.text = storyName

    }

    fun getValues(view: View?) {
        val nameEditText = findViewById<View>(R.id.txv_titulo_capitulo) as EditText
        chapterName = nameEditText.text.toString()

        val contentEditText = findViewById<View>(R.id.txv_conteudo) as EditText
        content = contentEditText.text.toString()

        val notesEditText = findViewById<View>(R.id.txv_notas_iniciais) as EditText
        notes = notesEditText.text.toString()

        val endingNotesEditText = findViewById<View>(R.id.txv_notas_finais) as EditText
        endingNotes = endingNotesEditText.text.toString()

    }

    fun validateValues(): Boolean {
        if (storyName.isEmpty() || content.isEmpty()) {
            Toast.makeText(
                this,
                "Complete o formulário.",
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        return true
    }

    fun submitbuttonHandler(view: View?) {
        getValues(view)

        if (validateValues()) {

            val newChapter = CreateChapterDto(
                storyId,
                storyName,
                content,
                notes,
                endingNotes
            )

            val callChapterCreate =
                BeficBackendFactory().capituloBeficBackendService().save(newChapter);

            getChapterInfo(callChapterCreate)

        }
    }

    fun getChapterInfo(callback: Call<Capitulo>) {
        callback.enqueue(object : Callback<Capitulo> {
            override fun onFailure(call: Call<Capitulo>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Capitulo>,
                response: Response<Capitulo>
            ) {
                response.body()?.let {
                    Toast.makeText(
                        baseContext,
                        "Capítulo cadastrado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()

                    Handler()
                        .postDelayed(
                            Runnable {
                                val i = Intent(context, ChapterReadActivity::class.java)
                                i.putExtra("USER_ID", UserInfoUtils.userId);
                                i.putExtra("STORY_ID", it.historiaId);
                                i.putExtra("STORY_NAME", storyName);
                                i.putExtra("CHAPTER_NUMBER", it.numero);
                                startActivity(i)
                            }, 2000
                        )

                }
            }
        })
    }

    fun goToHome(view: View?) {
        val btnHome = findViewById<View>(R.id.home)

        val i = Intent(context, UserProfileActivity::class.java)
        i.putExtra("USER_ID", UserInfoUtils.userId);
        startActivity(i)

    }
}