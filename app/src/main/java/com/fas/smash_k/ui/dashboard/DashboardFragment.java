package com.fas.smash_k.ui.dashboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.fas.smash_k.R;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;


import java.util.List;

public class DashboardFragment extends Fragment implements OnMapReadyCallback, OnLocationClickListener, PermissionsListener, OnCameraTrackingChangedListener {

    private DashboardViewModel dashboardViewModel;
    private MapView dMapView;
    private  View mview;
    private MapboxMap dmap;
    private PermissionsManager permissionsManager;
    private  LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;

    private PermissionsManager dpermissionsManager;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;
    private boolean isInTrackingMode;

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        dMapView.onStart();
//    }

    @Override
    public void onResume() {
        super.onResume();
        dMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        dMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        dMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        dMapView.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dMapView.onFinishTemporaryDetach();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // mview = ViewModelProviders.of(this).get(DashboardViewModel.class);
        Mapbox.getInstance(getActivity(), getString(R.string.access_token));
        mview = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //  final TextView textView = root.findViewById(R.id.text_dashboard);


        dMapView = mview.findViewById(R.id.mapView);
        dMapView.onCreate(savedInstanceState);
//        dMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull MapboxMap mapboxMap) {
//               // mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/fas96/ckav94e4b2gz71is1rbxpcgff/edit/#12/48.8665/2.3176"));
//                mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/fas96/ckav94e4b2gz71is1rbxpcgff"));
//            }
//        });

        dMapView.getMapAsync(this);

//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return mview;
//    }
//
//    @Override
//    public void onPermissionResult(boolean granted) {
//        if(granted){
//            enableLocation();
//        }
//    }
//
//    private void initializeLocationEngine(){
//        locationEngine= LocationEngineProvider.getBestLocationEngine(getActivity());
//
//        locationEngine.notifyAll();
//
//
//
//    }


//        // private void initializeLocationLayer(){}
//
//
//        @Override
//        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
//        @NonNull int[] grantResults){
//            permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//
//        private void enableLocation () {
//            if (PermissionsManager.areLocationPermissionsGranted(getActivity())) {
//                initializeLocationEngine();
//                initializeLocationLayer();
//
//            } else {
//                permissionsManager = new PermissionsManager(this);
//                permissionsManager.requestLocationPermissions(getActivity());
//            }
//        }
//        @Override
//        public void onMapReady (@NonNull MapboxMap mapboxMap){
//
//            dmap = mapboxMap;
//            enableLocation();
//        }
//
//
//        @Override
//        public void onExplanationNeeded (List < String > permissionsToExplain) {
//            //present toast or dialog.
//
//        }


        return mview;
    }
    //add

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.SATELLITE,(style)->{enableLocationComponent(style);});
//        mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//                enableLocationComponent(style);
//
//            }
//        });
    }

    @SuppressLint("WrongConstant")
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {

// Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(getActivity())
                    .elevation(5)
                    .accuracyAlpha(.6f)
                    .accuracyColor(Color.RED)
                    .foregroundDrawable(R.drawable.android_custom_location_icon)
                    .build();

// Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(getActivity(), loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build();

// Activate with options
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

// Add the location icon click listener
            locationComponent.addOnLocationClickListener(this);

// Add the camera tracking listener. Fires if the map camera is manually moved.
            locationComponent.addOnCameraTrackingChangedListener(this);

            mview.findViewById(R.id.back_to_camera_tracking_mode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isInTrackingMode) {
                        isInTrackingMode = true;
                        locationComponent.setCameraMode(CameraMode.TRACKING);
                        locationComponent.zoomWhileTracking(16f);
                        Toast.makeText(getActivity(), getString(R.string.tracking_enabled),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.tracking_already_enabled),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public void onLocationComponentClick() {
        if (locationComponent.getLastKnownLocation() != null) {
            String str_here= locationComponent.getLastKnownLocation().getLatitude()+""+locationComponent.getLastKnownLocation().getLongitude();
            Toast.makeText(getActivity(),str_here, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCameraTrackingDismissed() {
        isInTrackingMode = false;
    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {
// Empty on purpose
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getActivity(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle((style ->{enableLocationComponent(style);}));
        } else {
            Toast.makeText(getActivity(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();

        }
    }

}