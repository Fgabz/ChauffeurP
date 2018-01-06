package com.example.cp.chauffeurp.ui.home;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cp.chauffeurp.ChauffeurPApp;
import com.example.cp.chauffeurp.R;
import com.example.cp.chauffeurp.ui.base.BaseFragment;
import com.example.cp.chauffeurp.util.PermissionUtil;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEnginePriority;
import com.mapbox.services.android.telemetry.location.LostLocationEngine;

import butterknife.BindView;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {

    @BindView(R.id.mapView)
    MapView mapView;

    private MapboxMap mapboxMap;
    private LocationLayerPlugin locationPlugin;
    private LocationEngine locationEngine;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected void initializeDependencyInjector() {
        ChauffeurPApp.getApp(getContext()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setLayoutId(R.layout.fragment_home);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                HomeFragment.this.mapboxMap = mapboxMap;
                enableLocationPlugin();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void enableLocationPlugin() {
        // Check if permissions are enabled and if not request
        if (PermissionUtil.hasFineLocationPermission(getActivity())) {
            // Create an instance of LOST location engine
            initializeLocationEngine();

            locationPlugin = new LocationLayerPlugin(mapView, mapboxMap, locationEngine);
            locationPlugin.setLocationLayerEnabled(LocationLayerMode.TRACKING);
        } else {
            PermissionUtil.requestFineLocationPermission(getActivity());
        }
    }

    @SuppressLint("MissingPermission")
    private void initializeLocationEngine() {
        locationEngine = new LostLocationEngine(getContext());
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            setCameraPosition(lastLocation);
            setMarkerPosition(lastLocation);
        }
    }

    private void setMarkerPosition(Location location) {
        mapboxMap.addMarker(new MarkerViewOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
        );
    }

    private void setCameraPosition(Location location) {
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 16));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState)
        ;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
