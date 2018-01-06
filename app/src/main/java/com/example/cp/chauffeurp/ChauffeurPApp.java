package com.example.cp.chauffeurp;

import android.app.Application;
import android.content.Context;

import com.mapbox.mapboxsdk.Mapbox;

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

        Mapbox.getInstance(getApplicationContext(), BuildConfig.MAPBOX_KEY);

        //Initialization Dagger
        initDagger();
    }

    private void initDagger() {
        if (appComponent == null) {
            appComponent = prepareApplicationComponent();
        }
    }

    private ChauffeurPComponent prepareApplicationComponent() {
        return DaggerChauffeurPComponent.builder()
                .chauffeurPModule(new ChauffeurPModule(this))
                .build();
    }

    public ChauffeurPComponent getAppComponent() {
        return appComponent;
    }
}
