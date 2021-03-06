package com.example.cp.chauffeurp;

import android.app.Application;
import android.content.Context;

import com.example.cp.chauffeurp.data.ApiModule;
import com.mapbox.mapboxsdk.Mapbox;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public class ChauffeurPApp extends Application {

    private ChauffeurPComponent appComponent;

    public static ChauffeurPApp getApp(Context ctx) {
        return (ChauffeurPApp) ctx.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialization Dagger
        Mapbox.getInstance(this, BuildConfig.MAPBOX_KEY);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initRealm();
        initDagger();
    }

    private void initRealm() {
        //Initialization of Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void initDagger() {
        if (appComponent == null) {
            appComponent = prepareApplicationComponent();
        }
    }

    private ChauffeurPComponent prepareApplicationComponent() {
        return DaggerChauffeurPComponent.builder()
                .chauffeurPModule(new ChauffeurPModule(this))
                .apiModule(new ApiModule())
                .build();
    }

    public ChauffeurPComponent getAppComponent() {
        return appComponent;
    }
}
