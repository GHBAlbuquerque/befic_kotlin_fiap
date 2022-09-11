package com.fiap.befic

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    var name = ""
    var email = ""
    var senha = ""
    var senhaRepetida = ""
    var phone = ""
    var checkBoxState = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun getValues(view: View?) {
        val nameEditText = findViewById<View>(R.id.name) as EditText
        name = nameEditText.text.toString()

        val emailEditText = findViewById<View>(R.id.email) as EditText
        email = emailEditText.text.toString()

        val senhaEditText = findViewById<View>(R.id.password) as EditText
        senha = senhaEditText.text.toString()

        val senhaRepetidaEditText = findViewById<View>(R.id.password_repeat) as EditText
        senhaRepetida = senhaRepetidaEditText.text.toString()

        val phoneEditText = findViewById<View>(R.id.phone) as EditText
        phone = phoneEditText.text.toString()

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
                val i = Intent(this, UserProfile::class.java)
                startActivity(i)
            }
        }
    }

    fun validateValues(): Boolean {
        if (name.isEmpty() || email.isEmpty() || senha.isEmpty() ||
            phone.isEmpty() || checkBoxState.equals(false) ||
            !senha.equals(senhaRepetida)
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

    fun radioButtonhandler(view: View?) {

        // Decide what happens when a user clicks on a button
    }
}