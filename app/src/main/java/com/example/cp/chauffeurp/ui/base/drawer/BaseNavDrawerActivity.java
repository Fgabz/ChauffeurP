package com.example.cp.chauffeurp.ui.base.drawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cp.chauffeurp.R;
import com.example.cp.chauffeurp.data.model.Address;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by fanilog on 02/03/2016.
 */
public abstract class BaseNavDrawerActivity extends AppCompatActivity implements DrawerAdapter.OnClickItemDrawer {


    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;
    protected ActionBarDrawerToggle drawerToggle;
    protected DrawerAdapter drawerAdapter;

    @Inject
    Realm realm;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.list_placeholder)
    TextView placeholdeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencyInjector();
    }


    protected abstract void initializeDependencyInjector();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_home);
        ViewGroup viewGroup = findViewById(R.id.activity_root_content);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        ButterKnife.bind(this);
        setUpToolBar();
        setRecyclerView();
    }

    private void setRecyclerView() {
        //Should be done in a Presenter class
        RealmResults<Address> list = realm.where(Address.class).findAll();
        if (list.size() > 0) {
            drawerAdapter = new DrawerAdapter(this, list);
            recyclerView.setAdapter(drawerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            placeholdeView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    protected void setUpToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
            drawerLayout.addDrawerListener(drawerToggle);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerToggle != null) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }
}
