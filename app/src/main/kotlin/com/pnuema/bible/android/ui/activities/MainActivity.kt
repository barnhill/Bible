package com.pnuema.bible.android.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.navigation.NavigationView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.ActivityMainBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.DeepLinks
import com.pnuema.bible.android.ui.bookchapterverse.NotifySelectionCompleted
import com.pnuema.bible.android.ui.read.ReadFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    NotifySelectionCompleted {
    private lateinit var readFragment: ReadFragment

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeepLinks.handleDeepLinks(intent)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.bringToFront()
        navigationView.requestLayout()

        readFragment = supportFragmentManager.findFragmentById(R.id.read_fragment) as ReadFragment
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
        val id = item.itemId

        if (id == R.id.nav_settings) {
            //TODO add settings
        } else if (id == R.id.nav_about) {
            Intent(this, OssLicensesMenuActivity::class.java).apply {
                putExtra("title", getString(R.string.license_screen_title))
                startActivity(this)
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSelectionComplete(book: Int, chapter: Int, verse: Int) {
        CurrentSelected.book = book
        CurrentSelected.chapter = chapter
        CurrentSelected.verse = verse
        readFragment.refresh()
    }
}
