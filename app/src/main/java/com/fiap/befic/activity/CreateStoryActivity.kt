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
import com.fiap.befic.data.dto.CreateStoryDto
import com.fiap.befic.data.entity.Historia
import com.fiap.befic.data.entity.Usuario
import com.fiap.befic.utils.UserInfoUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateStoryActivity : AppCompatActivity() {

    lateinit var context: Context
    var storyName = ""
    var sinopsis = ""
    var checkBoxState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_story)
        context = this;
    }

    fun getValues(view: View?) {
        val nameEditText = findViewById<View>(R.id.txv_nome_historia) as EditText
        storyName = nameEditText.text.toString()

        val sinopsisEditText = findViewById<View>(R.id.txv_sinopse) as EditText
        sinopsis = sinopsisEditText.text.toString()

        //initiate a check box
        val conditionsCheckBox = findViewById<View>(R.id.conditions) as CheckBox

        //check current state of the check box
        checkBoxState = conditionsCheckBox.isChecked
    }

    fun validateValues(): Boolean {
        if (storyName.isEmpty() || sinopsis.isEmpty() || checkBoxState.equals(false)
        ) {
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


            val newStory = CreateStoryDto(
                2,
                storyName,
                sinopsis
            )

            val callStoryCreate =
                BeficBackendFactory().historiaBeficBackendService().save(newStory);

            getStoryInfo(callStoryCreate)

        }
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
                    Toast.makeText(
                        baseContext,
                        "História cadastrada com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()

                    val i = Intent(context, CreateChapterActivity::class.java)
                    i.putExtra("STORY_ID", it.id);
                    i.putExtra("STORY_NAME", it.nome);
                    startActivity(i)

                }
            }
        })
    }
}