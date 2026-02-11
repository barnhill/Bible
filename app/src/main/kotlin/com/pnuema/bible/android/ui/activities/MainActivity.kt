package com.pnuema.bible.android.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.DeepLinks
import com.pnuema.bible.android.ui.bookchapterverse.NotifySelectionCompleted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main),
    NavigationView.OnNavigationItemSelectedListener,
    NotifySelectionCompleted {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        DeepLinks.handleDeepLinks(intent)

        findViewById<NavigationView>(R.id.nav_view).apply {
            setNavigationItemSelectedListener(this@MainActivity)
            bringToFront()
            requestLayout()
        }
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch {
            CurrentSelected.savePreferences()
        }
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        DeepLinks.handleDeepLinks(intent)
        super.onNewIntent(intent)
    }

    override fun onResume() {
        runBlocking {
            CurrentSelected.readPreferences()
        }

        super.onResume()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        /*val id = item.itemId

        if (id == R.id.nav_settings) {
            //TODO add settings
        }*/

        return super.onOptionsItemSelected(item)
    }

    override fun onSelectionComplete(book: Int, chapter: Int, verse: Int) {
        CurrentSelected.book = book
        CurrentSelected.chapter = chapter
        CurrentSelected.verse = verse
    }
}
