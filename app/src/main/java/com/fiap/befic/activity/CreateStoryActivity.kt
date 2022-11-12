package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.data.Historia
import com.fiap.befic.data.Usuario
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

            val btnEnviar = findViewById<Button>(R.id.button_enviar)

            btnEnviar.setOnClickListener {

                val author = Usuario(
                    UserInfoUtils.userId,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                )

                val newStory = Historia(
                    null,
                    author,
                    storyName,
                    null,
                    null,
                    sinopsis
                )

                val callStoryCreate =
                    BeficBackendFactory().historiaBeficBackendService().save(newStory);

                getStoryInfo(callStoryCreate)
            }
        }
    }

    //TODO: ver e terminar
    //TODO: criar botao adicionar capitulo
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
                        Log.i("log", "chegou aqui")

                }
            }
        })
    }
}