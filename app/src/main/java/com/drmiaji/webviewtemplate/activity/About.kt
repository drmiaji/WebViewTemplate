package com.drmiaji.webviewtemplate.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.net.toUri
import com.drmiaji.webviewtemplate.R

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val toolbar = findViewById<Toolbar?>(R.id.app_bar)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mMenuInflater = menuInflater
        mMenuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        } else if (itemId == R.id.share) {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.setType("text/plain")
            val shareSub: String? = getString(R.string.share_subject)
            val shareBody: String? = getString(R.string.share_message)
            myIntent.putExtra(Intent.EXTRA_TEXT, shareSub)
            myIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(myIntent, "Share using!"))
        } else if (itemId == R.id.more_apps) {
            val moreApp = Intent(Intent.ACTION_VIEW)
            moreApp.setData("https://play.google.com/store/apps/details?id=com.drmiaji.tajweed".toUri())
            startActivity(moreApp)
        } else if (itemId == R.id.action_content) {
            startActivity(Intent(this, About::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    fun goToChap(view: View) {
        val id = view.id
        if (id == R.id.rateUs) {
            val moreApp = Intent(Intent.ACTION_VIEW)
            moreApp.setData("https://play.google.com/store/search?q=drmiaji".toUri())
            startActivity(moreApp)
        } else if (id == R.id.link) {
            val link = Intent(Intent.ACTION_VIEW)
            link.setData("http://www.drmiaji.com".toUri())
            startActivity(link)
        }
    }
}