package com.fiap.befic.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R
import com.fiap.befic.utils.UserInfoUtils

class NavigationBarActivity : AppCompatActivity() {

    lateinit var context: Context
    var loggedUserName = "username";
    var loggedUserId = 0L;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_bar)
        getInfo()
    }

    fun goToHome(view: View?) {
        val btnHome = findViewById<View>(R.id.home)

        btnHome.setOnClickListener {

            val i = Intent(context, UserProfileActivity::class.java)
            i.putExtra("USER_ID", loggedUserId);
            startActivity(i)
        }
    }

    fun getInfo() {
        loggedUserId = UserInfoUtils.userId
        loggedUserName = UserInfoUtils.username

        val username = findViewById<View>(R.id.txv_username) as TextView
        username.text = loggedUserName

        val id = findViewById<View>(R.id.txv_user_id) as TextView
        id.text = loggedUserId.toString()
    }
}