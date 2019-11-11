package com.pnuema.android.bible.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.navigation.NavigationView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.statics.DeepLinks
import com.pnuema.android.bible.ui.dialogs.NotifySelectionCompleted
import com.pnuema.android.bible.ui.dialogs.NotifyVersionSelectionCompleted
import com.pnuema.android.bible.ui.fragments.ReadFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, NotifySelectionCompleted, NotifyVersionSelectionCompleted {
    private var readFragment: ReadFragment? = null

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

        gotoRead()
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

    private fun gotoRead() {
        if (readFragment == null) {
            readFragment = ReadFragment.newInstance()
            val ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.fragment_container, readFragment!!, ReadFragment::class.java.simpleName)
            ft.commit()
        } else {
            readFragment!!.refresh()
        }
    }

    override fun onSelectionPreloadChapter(book: Int, chapter: Int) {
        CurrentSelected.book = book
        CurrentSelected.chapter = chapter
        gotoRead()
    }

    override fun onSelectionComplete(book: Int, chapter: Int, verse: Int) {
        CurrentSelected.book = book
        CurrentSelected.chapter = chapter
        CurrentSelected.verse = verse
        gotoRead()
    }

    override fun onSelectionComplete(version: String) {
        CurrentSelected.version = version
        gotoRead()
    }
}
