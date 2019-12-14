package com.pnuema.bible.android.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.navigation.NavigationView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.DeepLinks
import com.pnuema.bible.android.ui.dialogs.NotifySelectionCompleted
import com.pnuema.bible.android.ui.dialogs.NotifyVersionSelectionCompleted
import com.pnuema.bible.android.ui.fragments.ReadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, NotifySelectionCompleted, NotifyVersionSelectionCompleted {
    private lateinit var readFragment: ReadFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeepLinks.handleDeepLinks(intent)

        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.bringToFront()
        navigationView.requestLayout()

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        readFragment = supportFragmentManager.findFragmentById(R.id.read_fragment) as ReadFragment
    }

    override fun onPause() {
        CurrentSelected.savePreferences()
        super.onPause()
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        DeepLinks.handleDeepLinks(intent)
        super.onNewIntent(intent)
    }

    override fun onResume() {
        CurrentSelected.readPreferences()
        super.onResume()
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }

    override fun onSelectionPreloadChapter(book: Int, chapter: Int) {
        CurrentSelected.book = book
        CurrentSelected.chapter = chapter
        readFragment.refresh()
    }

    override fun onSelectionComplete(book: Int, chapter: Int, verse: Int) {
        CurrentSelected.book = book
        CurrentSelected.chapter = chapter
        CurrentSelected.verse = verse
        readFragment.refresh()
    }

    override fun onSelectionComplete(version: String) {
        CurrentSelected.version = version
        readFragment.refresh()
    }
}
