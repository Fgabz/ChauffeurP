package com.example.cp.chauffeurp.data;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fanilogabaud on 07/01/2018.
 */

@Module
public class DataModule {

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
}
