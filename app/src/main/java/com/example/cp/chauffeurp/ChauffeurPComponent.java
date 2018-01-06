package com.example.cp.chauffeurp;

import com.example.cp.chauffeurp.ui.MainActivity;
import com.example.cp.chauffeurp.ui.home.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

@Singleton
@Component(modules = {ChauffeurPModule.class})
public interface ChauffeurPComponent {
    void inject(MainActivity mainActivity);
    void inject(HomeFragment homeFragment);
}
