package com.fiap.befic.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R


class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val stories = ArrayList<String>()
        stories.addAll(
            listOf<String>(
                "Partners in crime", "Meu Vizinho Mafioso", "When I Kissed The Teacher," +
                        "Até o Outro Luar", "Coisas extraordinárias"
            )
        )

        val storyList = findViewById<ListView>(R.id.storyList)

        val arrayadapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            stories
        )
        storyList!!.adapter = arrayadapter
        setListViewHeightBasedOnChildren(storyList)

        storyList.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            view.setOnClickListener {
                val i = Intent(this, StoryReadActivity::class.java)
                startActivity(i)
            }
        })
    }

    fun setListViewHeightBasedOnChildren(myListView: ListView?) {
        val adapter: ListAdapter = myListView!!.adapter
        var totalHeight = 0
        for (i in 0 until adapter.getCount()) {
            val item: View = adapter.getView(i, null, myListView)
            item.measure(0, 0)
            totalHeight += item.measuredHeight
        }
        val params = myListView.layoutParams
        params.height = totalHeight + myListView.dividerHeight * (adapter.getCount() - 1)
        myListView.layoutParams = params
    }


}