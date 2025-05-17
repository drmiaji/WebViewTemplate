package com.drmiaji.webviewtemplate.ui

import android.content.Intent
import androidx.core.graphics.drawable.DrawableCompat
import android.os.Bundle
import android.view.MenuItem
import com.drmiaji.webviewtemplate.R
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
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

        // Set up the back button in the action bar
        supportActionBar?.apply {
            title = getString(R.string.app_name) // ✅ Use getString in an Activity context
            setDisplayHomeAsUpEnabled(true)

            // Option 1: Tint a custom back arrow
            // setHomeAsUpIndicator(R.drawable.ic_arrow_back) // Optional: set custom back arrow
        }

        // Change the color of the navigation icon (back arrow) - non-deprecated approach
        toolbar.navigationIcon?.let { originalDrawable ->
            val wrappedDrawable = DrawableCompat.wrap(originalDrawable).mutate()
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(this, R.color.white))
            toolbar.navigationIcon = wrappedDrawable
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

    // Handle the back button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Go back when the back button is pressed
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}