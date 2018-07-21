package com.pnuema.bible.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.pnuema.bible.R;
import com.pnuema.bible.data.IBook;
import com.pnuema.bible.data.IChapter;
import com.pnuema.bible.data.IVerse;
import com.pnuema.bible.data.IVersion;
import com.pnuema.bible.statics.CurrentSelected;
import com.pnuema.bible.ui.dialogs.BCVDialog;
import com.pnuema.bible.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.bible.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.bible.ui.fragments.ReadFragment;
import com.pnuema.bible.ui.utils.DialogUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NotifySelectionCompleted, NotifyVersionSelectionCompleted {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.requestLayout();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        CurrentSelected.savePreferences();
        super.onPause();
    }

    @Override
    protected void onResume() {
        CurrentSelected.readPreferences();
        super.onResume();

        //if no version was selected prompt the user to select an initial version
        if (CurrentSelected.getVersion() == null) {
            DialogUtils.showVersionsPicker(this, this);
        } else if (CurrentSelected.getBook() != null && CurrentSelected.getChapter() != null) {
            gotoRead();
        } else {
            DialogUtils.showBookChapterVersePicker(this, BCVDialog.BCV.BOOK, this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, OssLicensesMenuActivity.class);
            String title = getString(R.string.license_screen_title);
            intent.putExtra("title", title);
            startActivity(intent);
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    private void gotoRead() {
        if (CurrentSelected.getVersion() != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragment_container, ReadFragment.newInstance(), ReadFragment.class.getSimpleName());
            ft.commit();
        }
    }

    @Override
    public void onSelectionPreloadChapter(IBook book, IChapter chapter) {
        CurrentSelected.setBook(book);
        CurrentSelected.setChapter(chapter);
        gotoRead();
    }

    @Override
    public void onSelectionComplete(IBook book, IChapter chapter, IVerse verse) {
        CurrentSelected.setBook(book);
        CurrentSelected.setChapter(chapter);
        CurrentSelected.setVerse(verse);
        gotoRead();
    }

    @Override
    public void onSelectionComplete(IVersion version) {
        CurrentSelected.setVersion(version);
        gotoRead();
    }
}
