package com.fiap.befic.activity

import BeficBackendFactory
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.fiap.befic.R


class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val callStoriesByUser = BeficBackendFactory().historiaBeficBackendService().findByAutor(1);

        //TODO: TRY CATCH?
        val storiesByUser = callStoriesByUser.execute();

        if (storiesByUser.isSuccessful) {

            val storiesNames = storiesByUser.body()?.map { it.nome }
            val stories = storiesNames as ArrayList<String>

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
        /*
        callStoriesByUser.enqueue(object : Callback<List<Historia>> {

            override fun onResponse(
                call: Call<List<Historia>>,
                response: Response<List<Historia>>
            ) {

                response.body()?.let {
                    Log.i("Historia", it.toString())
                    Toast.makeText(this@UserProfileActivity, it.toString(), Toast.LENGTH_LONG)
                        .show()
                } ?: Toast.makeText(
                    this@UserProfileActivity,
                    "Não há histórias para este autor.",
                    Toast.LENGTH_LONG
                )
                    .show()

            }

            override fun onFailure(call: Call<CEP>?, t: Throwable?) {
                t?.message?.let { it1 -> Log.e("Erro", it1) }
            }
        })

        val stories = ArrayList<String>()
        stories.addAll(
            listOf<String>(
                "Partners in crime", "Meu Vizinho Mafioso", "When I Kissed The Teacher," +
                        "Até o Outro Luar", "Coisas extraordinárias"
            )
        )*/

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