package com.drmiaji.webviewtemplate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drmiaji.webviewtemplate.models.ChapterItem
import com.drmiaji.webviewtemplate.R

class ChapterAdapter(
    private val items: List<ChapterItem>,
    private val onClick: (ChapterItem) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleText: TextView = view.findViewById(R.id.chapter_title)
        fun bind(item: ChapterItem) {
            titleText.text = item.title
            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}