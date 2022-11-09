package com.fiap.befic.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R

class StoryReadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getSerializableExtra("USER_ID") as Long
        val storyId = intent.getSerializableExtra("STORY_ID") as Long
        setContentView(R.layout.activity_story_read)
    }
}