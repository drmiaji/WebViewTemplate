package com.drmiaji.webviewtemplate.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drmiaji.webviewtemplate.databinding.ItemChapterBinding
import com.drmiaji.webviewtemplate.models.ChapterItem
import com.drmiaji.webviewtemplate.utils.HighlightSpanBuilder

class ChapterAdapter(
    private var items: List<ChapterItem>, // ← now it's mutable (var)
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

    // Add this property to hold current search query
    private var searchQuery: String = ""
    private var highlightSpanBuilder: HighlightSpanBuilder? = null

    inner class ViewHolder(private val binding: ItemChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChapterItem) {
            val context = binding.root.context

            // Initialize highlightSpanBuilder if it's null
            if (highlightSpanBuilder == null) {
                highlightSpanBuilder = HighlightSpanBuilder(context)
            }

            // Highlight the search term if it exists
            if (searchQuery.isNotEmpty()) {
                binding.chapterTitle.text = highlightSpanBuilder?.getHighlightedText(item.title, searchQuery)
            } else {
                binding.chapterTitle.text = item.title
            }

            val typeface = getSolaimanTypeface(context)
            binding.chapterTitle.setTypeface(typeface, Typeface.BOLD)

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

    fun updateData(newList: List<ChapterItem>, query: String = "") {
        items = newList
        searchQuery = query
        notifyDataSetChanged()
    }
}