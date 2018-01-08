package com.example.cp.chauffeurp.ui.base.drawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.cp.chauffeurp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanilog on 02/03/2016.
 */
public abstract class BaseNavDrawerActivity extends AppCompatActivity implements DrawerAdapter.OnClickItemDrawer {


    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;

    protected ActionBarDrawerToggle drawerToggle;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.drawer_container, NavDrawerFragment.newInstance(),
                            NavDrawerFragment.class.getSimpleName())
                    .commit();
        }*/
    }


    protected abstract void initializeDependencyInjector();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_home);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_root_content);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        ButterKnife.bind(this);
        setUpToolBar();
    }


    protected void setUpToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
            //drawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_black);
            drawerToggle.setDrawerIndicatorEnabled(false);

            toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerToggle != null) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }
}
