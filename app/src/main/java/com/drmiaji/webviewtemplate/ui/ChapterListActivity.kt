package com.drmiaji.webviewtemplate.ui

import android.content.Intent
import android.os.Bundle
import com.drmiaji.webviewtemplate.R
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drmiaji.webviewtemplate.activity.BaseActivity
import com.drmiaji.webviewtemplate.adapter.ChapterAdapter
import com.drmiaji.webviewtemplate.viewmodel.ChapterViewModel

class ChapterListActivity : BaseActivity() {

    private val viewModel: ChapterViewModel by viewModels()

    override fun getLayoutResource() = R.layout.activity_chapter_list

    override fun onActivityReady(savedInstanceState: Bundle?) {
        val recyclerView = findViewById<RecyclerView>(R.id.chapter_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.chapters.observe(this) { list ->
            recyclerView.adapter = ChapterAdapter(list) { chapter ->
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("fileName", chapter.file)
                startActivity(intent)
            }
        }
    }
}