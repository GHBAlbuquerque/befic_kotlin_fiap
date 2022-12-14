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
import com.fiap.befic.data.dto.CreateUsuarioLoginDto
import com.fiap.befic.data.entity.Login
import com.fiap.befic.utils.DateInputMask
import com.fiap.befic.utils.LocalDateUtils
import com.fiap.befic.utils.UserInfoUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    val context = this
    var userId = 0L
    var loginId = 0L
    var username = ""
    var email = ""
    var name = ""
    var birthday = ""
    var phone = ""
    var gender = ""
    var senha = ""
    var senhaRepetida = ""
    var checkBoxState = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val dateEditText = findViewById<View>(R.id.birthday) as EditText
        DateInputMask(dateEditText)
    }

    fun getValues(view: View?) {
        val nameEditText = findViewById<View>(R.id.username) as EditText
        username = nameEditText.text.toString()

        val emailEditText = findViewById<View>(R.id.email) as EditText
        email = emailEditText.text.toString()

        val nomeEditText = findViewById<View>(R.id.name) as EditText
        name = nomeEditText.text.toString()

        val dateEditText = findViewById<View>(R.id.birthday) as EditText
        birthday = dateEditText.text.toString()

        val phoneEditText = findViewById<View>(R.id.phone) as EditText
        phone = phoneEditText.text.toString()

        val senhaEditText = findViewById<View>(R.id.password) as EditText
        senha = senhaEditText.text.toString()

        val senhaRepetidaEditText = findViewById<View>(R.id.password_repeat) as EditText
        senhaRepetida = senhaRepetidaEditText.text.toString()

        val radioGroup = findViewById(R.id.radioGroup) as RadioGroup
        val checkedRadio = radioGroup.checkedRadioButtonId
        val radioButton = findViewById(checkedRadio) as RadioButton
        val radioButtonText = radioButton.text.toString()

        gender = when (radioButtonText) {
            "F" -> "FEMININO"
            "M" -> "MASCULINO"
            "outro" -> "OUTRO"
            else -> {
                "OUTRO"
            }
        }

        //initiate a check box
        val conditionsCheckBox = findViewById<View>(R.id.conditions) as CheckBox

        //check current state of the check box
        checkBoxState = conditionsCheckBox.isChecked


    }

    fun submitbuttonHandler(view: View?) {
        getValues(view)

        if (validateValues()) {

            val btnEnviar = findViewById<Button>(R.id.button_enviar)

            btnEnviar.setOnClickListener {

                val localdate = LocalDateUtils.getDate(birthday)

                val newUserLogin = CreateUsuarioLoginDto(
                    username,
                    senha,
                    name,
                    localdate.year,
                    localdate.monthValue,
                    localdate.dayOfMonth,
                    phone,
                    email,
                    gender
                )

                val callLoginInfo =
                    BeficBackendFactory().loginBeficBackendService().save(newUserLogin);

                getLoginInfo(callLoginInfo, context)
            }
        }
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
                        UserInfoUtils.userId = userId;
                        UserInfoUtils.username = username;

                        val i = Intent(context, UserProfileActivity::class.java)
                        i.putExtra("USER_ID", userId);
                        i.putExtra("LOGIN_ID", loginId);
                        startActivity(i)

                    }
                } ?: Toast.makeText(
                    baseContext,
                    "Erro ao cadastrar. Verifique as informa????es e tente novamente.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

    fun validateValues(): Boolean {
        if (username.isEmpty() || email.isEmpty() || senha.isEmpty() ||
            phone.isEmpty() || checkBoxState.equals(false)
        ) {
            Toast.makeText(
                this,
                "Complete o formul??rio.",
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        if (!senha.equals(senhaRepetida)) {
            Toast.makeText(
                this,
                "As senhas devem ser iguais!",
                Toast.LENGTH_SHORT
            ).show()
        }

        return true
    }

    fun radioButtonhandler(view: View?) {

        // Decide what happens when a user clicks on a button
    }
}