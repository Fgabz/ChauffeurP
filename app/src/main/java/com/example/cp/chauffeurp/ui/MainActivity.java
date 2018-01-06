package com.example.cp.chauffeurp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cp.chauffeurp.ChauffeurPApp;
import com.example.cp.chauffeurp.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ChauffeurPApp.getApp(this).getAppComponent().inject(this);
    }
}
