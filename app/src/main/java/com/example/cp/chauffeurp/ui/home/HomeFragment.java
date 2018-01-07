package com.example.cp.chauffeurp.ui.home;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cp.chauffeurp.BuildConfig;
import com.example.cp.chauffeurp.ChauffeurPApp;
import com.example.cp.chauffeurp.R;
import com.example.cp.chauffeurp.ui.base.BaseFragment;
import com.example.cp.chauffeurp.util.PermissionUtil;
import com.mapbox.mapboxsdk.annotations.MarkerView;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
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
import com.mapbox.services.android.ui.geocoder.GeocoderAutoCompleteView;
import com.mapbox.services.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.services.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.services.commons.models.Position;

import butterknife.BindView;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView, MapboxMap.OnCameraIdleListener {

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.autoCompleteWidget)
    GeocoderAutoCompleteView autoComplete;

    private MapboxMap mapboxMap;
    private LocationLayerPlugin locationPlugin;
    private LocationEngine locationEngine;

    private MarkerView userMarker;
    private MarkerView destMarker;

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

        autoComplete.setAccessToken(BuildConfig.MAPBOX_KEY);
        autoComplete.setType(GeocodingCriteria.TYPE_ADDRESS);
        autoComplete.setCountry("FR");

        autoComplete.setOnFeatureListener(new GeocoderAutoCompleteView.OnFeatureListener() {
            @Override
            public void onFeatureClick(CarmenFeature feature) {
                if (destMarker != null) {
                    mapboxMap.removeMarker(destMarker);
                }

                Position position = feature.asPosition();
                moveToPosition(new LatLng(position.getLatitude(), position.getLongitude()));
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
            moveToPosition(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
        }

        mapboxMap.setOnCameraIdleListener(this);
    }

    private void updateDestinationMakerPosition() {
        CameraPosition cameraPosition = mapboxMap.getCameraPosition();
        LatLng pos = cameraPosition.target;

        if (destMarker != null) {
            ValueAnimator markerAnimator = ObjectAnimator.ofObject(destMarker, "position",
                    new LatLngEvaluator(), destMarker.getPosition(), pos);
            markerAnimator.setDuration(2000);
            markerAnimator.start();

            presenter.retrievePlaceNameFromPosition(pos.getLongitude(), pos.getLatitude());
        } else {
            setMarkerPosition(pos);
        }
    }

    private void moveToPosition(LatLng latLng) {
        setCameraPosition(latLng);
        setMarkerPosition(latLng);
    }

    private void setMarkerPosition(LatLng latLng) {
        if (userMarker == null) {
            userMarker = mapboxMap.addMarker(new MarkerViewOptions().position(latLng));
        } else {
            destMarker = mapboxMap.addMarker(new MarkerViewOptions().position(latLng));
        }
    }

    private void setCameraPosition(LatLng latLngn) {
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngn, 16));
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
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onCameraIdle() {
        updateDestinationMakerPosition();
    }

    @Override
    public void onPlaceNameFound(String placeName) {

    }

    private static class LatLngEvaluator implements TypeEvaluator<LatLng> {
        // Method is used to interpolate the marker animation.

        private LatLng latLng = new LatLng();

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    }
}
