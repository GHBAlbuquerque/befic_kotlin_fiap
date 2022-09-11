package com.fiap.befic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCliqueAqui = findViewById<Button>(R.id.btn_clique_aqui)

        btnCliqueAqui.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
           startActivity(i)
        }
    }

}