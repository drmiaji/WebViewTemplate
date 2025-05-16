package com.drmiaji.webviewtemplate.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drmiaji.webviewtemplate.databinding.ItemChapterBinding
import com.drmiaji.webviewtemplate.models.ChapterItem

class ChapterAdapter(
    private val items: List<ChapterItem>,
    private val onClick: (ChapterItem) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    companion object {
        private var solaimanTypeface: Typeface? = null

        fun getSolaimanTypeface(context: Context): Typeface {
            if (solaimanTypeface == null) {
                solaimanTypeface = Typeface.createFromAsset(context.assets, "font/solaimanlipi.ttf")
            }
            return solaimanTypeface!!
        }
    }

    inner class ViewHolder(private val binding: ItemChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChapterItem) {
            binding.chapterTitle.text = item.title

            val typeface = getSolaimanTypeface(binding.root.context)
            binding.chapterTitle.setTypeface(typeface, Typeface.BOLD)  // Apply bold style here

            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}