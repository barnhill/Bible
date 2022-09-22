package com.pnuema.bible.android.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.navigation.NavigationView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.ActivityMainBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.DeepLinks
import com.pnuema.bible.android.ui.dialogs.NotifySelectionCompleted
import com.pnuema.bible.android.ui.fragments.ReadFragment
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, NotifySelectionCompleted {
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
            val intent = Intent(this, OssLicensesMenuActivity::class.java)
            val title = getString(R.string.license_screen_title)
            intent.putExtra("title", title)
            startActivity(intent)
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
