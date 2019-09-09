package com.pnuema.android.bible.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.material.navigation.NavigationView;
import com.pnuema.android.bible.R;
import com.pnuema.android.bible.statics.CurrentSelected;
import com.pnuema.android.bible.statics.DeepLinks;
import com.pnuema.android.bible.ui.dialogs.BCVDialog;
import com.pnuema.android.bible.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.android.bible.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.android.bible.ui.fragments.ReadFragment;
import com.pnuema.android.bible.ui.utils.DialogUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NotifySelectionCompleted, NotifyVersionSelectionCompleted {
    private ReadFragment readFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeepLinks.handleDeepLinks(getIntent());

        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.requestLayout();

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
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
    protected void onNewIntent(final Intent intent) {
        setIntent(intent);
        DeepLinks.handleDeepLinks(intent);
        super.onNewIntent(intent);
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
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {
            final Intent intent = new Intent(this, OssLicensesMenuActivity.class);
            final String title = getString(R.string.license_screen_title);
            intent.putExtra("title", title);
            startActivity(intent);
            return true;
        }

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    private void gotoRead() {
        if (CurrentSelected.getVersion() != null) {
            if (readFragment == null) {
                readFragment = ReadFragment.newInstance();
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, readFragment, ReadFragment.class.getSimpleName());
                ft.commit();
            } else {
                readFragment.refresh();
            }
        }
    }

    @Override
    public void onSelectionPreloadChapter(final int book, final int chapter) {
        CurrentSelected.setBook(book);
        CurrentSelected.setChapter(chapter);
        gotoRead();
    }

    @Override
    public void onSelectionComplete(final int book, final int chapter, final int verse) {
        CurrentSelected.setBook(book);
        CurrentSelected.setChapter(chapter);
        CurrentSelected.setVerse(verse);
        gotoRead();
    }

    @Override
    public void onSelectionComplete(final String version) {
        CurrentSelected.setVersion(version);
        gotoRead();
    }
}
