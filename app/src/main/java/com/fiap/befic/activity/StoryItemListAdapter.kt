package com.fiap.befic.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.fiap.befic.R
import com.fiap.befic.data.Historia

class StoryItemListAdapterr(
    private val context: Context,
    private val dataSource: ArrayList<Historia>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return dataSource[position].id;
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        // 1
        if (convertView == null) {

            // 2
            view = inflater.inflate(R.layout.story_item_list, parent, false)

            // 3
            holder = ViewHolder()
            holder.titleTextView = view.findViewById(R.id.story_title) as TextView
            holder.idTextView = view.findViewById(R.id.story_id) as TextView

            // 4
            view.tag = holder
        } else {
            // 5
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // 6
        val titleTextView = holder.titleTextView
        val idTextView = holder.idTextView

        val story = getItem(position) as Historia

        titleTextView.text = story.nome
        idTextView.text = story.id.toString()

        return view
    }

    private class ViewHolder {
        lateinit var titleTextView: TextView
        lateinit var idTextView: TextView
    }
}