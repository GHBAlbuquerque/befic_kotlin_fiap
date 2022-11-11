package com.fiap.befic.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.fiap.befic.R
import com.fiap.befic.data.Capitulo

class ChapterItemListAdapter(
    private val context: Context,
    private val dataSource: ArrayList<Capitulo>
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
        val id = dataSource[position].numero
        id?.let { return id } ?: return 0L
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        // 1
        if (convertView == null) {

            // 2
            view = inflater.inflate(R.layout.chapter_item_list, parent, false)

            // 3
            holder = ViewHolder()
            holder.titleTextView = view.findViewById(R.id.chapter_title) as TextView
            holder.numberTextView = view.findViewById(R.id.chapter_number) as TextView

            // 4
            view.tag = holder
        } else {
            // 5
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // 6
        val titleTextView = holder.titleTextView
        val numberTextView = holder.numberTextView

        val chapter = getItem(position) as Capitulo

        titleTextView.text = chapter.titulo
        numberTextView.text = chapter.numero.toString()

        return view
    }

    private class ViewHolder {
        lateinit var titleTextView: TextView
        lateinit var numberTextView: TextView
    }
}