package com.pnuema.simplebible.ui.activities;

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
import android.view.Menu;
import android.view.MenuItem;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.data.Verses;
import com.pnuema.simplebible.data.Versions;
import com.pnuema.simplebible.statics.CurrentSelected;
import com.pnuema.simplebible.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.simplebible.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.simplebible.ui.fragments.ReadFragment;
import com.pnuema.simplebible.ui.utils.DialogUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NotifySelectionCompleted, NotifyVersionSelectionCompleted {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //TODO if last used book chapter and verse are stored go to read fragment

        if (CurrentSelected.getVersion() == null) {
            DialogUtils.showVersionsPicker(this, this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoRead() {
        if (CurrentSelected.getVersion() != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, ReadFragment.newInstance());
            ft.commit();
        }
    }

    @Override
    public void onSelectionComplete(Books.Book book, Chapters.Chapter chapter, Verses.Verse verse) {
        CurrentSelected.setBook(book);
        CurrentSelected.setChapter(chapter);
        CurrentSelected.setVerse(verse);
        gotoRead();
    }

    @Override
    public void onSelectionComplete(Versions.Version version) {
        CurrentSelected.setVersion(version);
        gotoRead();
    }
}
