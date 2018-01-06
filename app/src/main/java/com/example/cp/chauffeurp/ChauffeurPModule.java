package com.example.cp.chauffeurp;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

@Module
public class ChauffeurPModule {

    private ChauffeurPApp chauffeurPApp;

    public ChauffeurPModule(ChauffeurPApp chauffeurPApp) {
        this.chauffeurPApp = chauffeurPApp;
    }

    @Provides
    @Singleton
    ChauffeurPApp provideApp() {
        return this.chauffeurPApp;
    }

    @Provides
    @Singleton
    Application provideApplication(ChauffeurPApp chauffeurPApp) {
        return chauffeurPApp;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return chauffeurPApp.getApplicationContext();
    }

}
