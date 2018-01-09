package com.example.cp.chauffeurp.ui;

import android.os.Bundle;

import com.example.cp.chauffeurp.ChauffeurPApp;
import com.example.cp.chauffeurp.R;
import com.example.cp.chauffeurp.data.model.Address;
import com.example.cp.chauffeurp.ui.base.drawer.BaseNavDrawerActivity;
import com.example.cp.chauffeurp.ui.home.HomeFragment;
import com.example.cp.chauffeurp.util.PermissionUtil;

public class MainActivity extends BaseNavDrawerActivity {

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChauffeurPApp.getApp(this).getAppComponent().inject(this);

        PermissionUtil.requestAppPermission(this);

        if (drawerAdapter != null) {
            drawerAdapter.setCallback(this);
        }

        homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment)
                    .commit();
        }
    }

    @Override
    protected void initializeDependencyInjector() {
        ChauffeurPApp.getApp(this).getAppComponent().inject(this);
    }

    @Override
    public void onClickItem(Address address) {
        if (homeFragment != null) {
            homeFragment.setAddress(address);
            drawerLayout.closeDrawers();
        }
    }
}
