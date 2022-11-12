package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.data.Login
import com.fiap.befic.data.LoginDto
import com.fiap.befic.utils.UserInfoUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    var username = ""
    var password = ""
    var userId = 0L;
    var loginId = 0L;
    lateinit var context: Context;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this;
    }

    fun getValues(view: View?) {
        val nameEditText = findViewById<View>(R.id.username) as EditText
        username = nameEditText.text.toString()

        val emailEditText = findViewById<View>(R.id.senha) as EditText
        password = emailEditText.text.toString()
    }

    fun validateLoginInfo(view: View?) {
        val loginDto = LoginDto(username, password);
        val callLoginInfo = BeficBackendFactory().loginBeficBackendService().entrar(loginDto);

        getLoginInfo(callLoginInfo, context)
    }

    fun getLoginInfo(callback: Call<Login>, context: Context) {
        callback.enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                Log.i("erro:", t.message.toString())
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Login>,
                response: Response<Login>
            ) {
                response.body()?.let {
                    userId = it.usuario.id!!
                    loginId = it.id!!

                    if (userId != 0L &&
                        loginId != 0L
                    ) {

                        val btnEnviar = findViewById<Button>(R.id.button_enviar)

                        btnEnviar.setOnClickListener {
                            UserInfoUtils.userId = userId;
                            UserInfoUtils.userName = username;

                            val i = Intent(context, UserProfileActivity::class.java)
                            i.putExtra("USER_ID", userId);
                            i.putExtra("LOGIN_ID", loginId);
                            startActivity(i)
                        }
                    }
                } ?: Toast.makeText(
                    baseContext,
                    "Erro ao entrar. Verifique as informações e tente novamente.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

    fun submitbuttonHandler(view: View?) {
        getValues(view)
        validateLoginInfo(view)
    }

}