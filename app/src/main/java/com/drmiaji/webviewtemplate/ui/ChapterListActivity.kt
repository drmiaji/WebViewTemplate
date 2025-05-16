package com.drmiaji.webviewtemplate.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.drmiaji.webviewtemplate.R
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drmiaji.webviewtemplate.activity.BaseActivity
import com.drmiaji.webviewtemplate.adapter.ChapterAdapter
import com.drmiaji.webviewtemplate.viewmodel.ChapterViewModel

class ChapterListActivity : BaseActivity() {

    private val viewModel: ChapterViewModel by viewModels()

    override fun getLayoutResource() = R.layout.activity_chapter_list

    override fun onActivityReady(savedInstanceState: Bundle?) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Chapters" // Or dynamic title if you want

        // Set custom font on toolbar title
        val typeface = ResourcesCompat.getFont(this, R.font.solaimanlipi)
        for (i in 0 until toolbar.childCount) {
            val view = toolbar.getChildAt(i)
            if (view is TextView && view.text == supportActionBar?.title) {
                view.typeface = typeface
                break
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.chapter_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.chapters.observe(this) { list ->
            recyclerView.adapter = ChapterAdapter(list) { chapter ->
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("fileName", chapter.file)
                intent.putExtra("title", chapter.title)
                startActivity(intent)
            }
        }
    }
}