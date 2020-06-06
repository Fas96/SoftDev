package com.fas.smash_k.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.fas.smash_k.R;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
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

import static android.os.Looper.getMainLooper;

public class DashboardFragment extends Fragment implements OnMapReadyCallback, OnLocationClickListener, LocationEngine, PermissionsListener, OnCameraTrackingChangedListener, LocationEngineListener {

    private DashboardViewModel dashboardViewModel;
    private MapView dMapView;
    private View mview;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;

    //for the explanation dailog

    Dialog MyDialoge;

    //location engine
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    //mapBox
    private MapView mapView;
    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;
    private boolean isInTrackingMode;
    private LocationEngineResult LocationEngineResult;

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //map box
        Mapbox.getInstance(getActivity(), getString(R.string.access_token));
        // loading the fragment
        mview = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //  final TextView textView = root.findViewById(R.id.text_dashboard);
        //loading the map box instance
        dMapView = mview.findViewById(R.id.mapView);
        dMapView.onCreate(savedInstanceState);

        dMapView.getMapAsync(this);

        return mview;
    }
    //add

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        //enableLocation();
        mapboxMap.setStyle(Style.TRAFFIC_NIGHT, (style) -> {
            enableLocationComponent(style);
        });

    }

    //tried
    @SuppressLint({"WrongConstant", "MissingPermission"})
    private void enableLocation(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            // Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(getContext(), loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

            // Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
            initializeLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @SuppressWarnings({"StatementWithEmptyBody", "MissingPermission"})
    public Location getLastLocation() {
        String packageName = getActivity().getPackageName();
        locationEngine.getLastLocation(null);
        return null;
    }

    @SuppressWarnings({"StatementWithEmptyBody", "MissingPermission"})
    private void initializeLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(getContext());

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        //   Location lastlocation = locationEngine.getLastLocation();
        LocationEngineCallback<LocationEngineResult> callback = null;
        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        setCameraPosition(originLocation);
        locationEngine.getLastLocation(callback);

        Location lastlocation = getLastLocation();
        if(lastlocation!=null){
            originLocation=lastlocation;
            setCameraPosition(lastlocation);
        }else {
          //  locationEngine.addLocationEngineListener(this);
        }
        System.out.println("Fas debug notify");
        locationEngine.notify();
        System.out.println("Fas here");

    }

    private void initializeLocationLayer() {
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode.NORMAL);
    }

    private void setCameraPosition(Location cameraPosition) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cameraPosition.getLatitude(), cameraPosition.getLongitude()), 13.0));
    }

    @Deprecated
    @SuppressWarnings({"MissingPermission", "StatementWithEmptyBody"})
    public void onConnected() {

        //locationEngine.requestLocationUpdates(new LocationEngineRequest());
        // locationEngine=locationEngine.getLastLocation();
        locationEngine.removeLocationUpdates((LocationEngineCallback<com.mapbox.android.core.location.LocationEngineResult>) LocationEngineResult.getLastLocation());


        LocationListener myLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    originLocation = location;
                    setCameraPosition(location);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }

        };

    }

    @Deprecated
    public void onLocationChanged(Location location) {
        if (location != null) {
            originLocation = location;
            setCameraPosition(location);
        }
    }

    @SuppressLint("WrongConstant")
    @SuppressWarnings({"MissingPermission"})
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
            locationComponent.setCameraMode(CameraMode.TRACKING
            );

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

                    } else {
                        /////
                    }
                }
            });

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @SuppressWarnings({"MissingPermission"})
    @Override
    public void onLocationComponentClick() {
        System.out.println("Fas: 1");
        if (locationComponent.getLastKnownLocation() != null) {
            String str_here = locationComponent.getLastKnownLocation().getLatitude() + "" + locationComponent.getLastKnownLocation().getLongitude();
            System.out.println("Fas: " + str_here);
            Toast.makeText(getActivity(), str_here, Toast.LENGTH_LONG).show();
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
    @SuppressWarnings("StatementWithEmptyBody")
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        MyDialoge=new Dialog(getContext());
        MyDialoge.setContentView(R.layout.permission);
        MyDialoge.setTitle("Why Location Permission is Required");
        MyDialoge.show();
        Button mok_btn = MyDialoge.findViewById(R.id.ok_btn);

        mok_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyDialoge.dismiss();

                mapboxMap = mapboxMap;
                //enableLocation();
                mapboxMap.setStyle(Style.OUTDOORS, (style) -> {
                    MyDialoge.cancel();
                    enableLocationComponent(style);
                });
                MyDialoge.cancel();
            }
        });
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle((style -> {
                enableLocationComponent(style);
            }));
        } else {
            Toast.makeText(getActivity(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();

        }
    }

    ///LocationEngine
    @Override
    public void getLastLocation(@NonNull LocationEngineCallback<LocationEngineResult> callback) throws SecurityException {
        originLocation = (Location) callback;

    }


    @Override
    public void requestLocationUpdates(@NonNull LocationEngineRequest request, @NonNull LocationEngineCallback<LocationEngineResult> callback, @Nullable Looper looper) throws SecurityException {

    }

    @Override
    public void requestLocationUpdates(@NonNull LocationEngineRequest request, PendingIntent pendingIntent) throws SecurityException {

    }

    @Override
    public void removeLocationUpdates(@NonNull LocationEngineCallback<LocationEngineResult> callback) {

    }

    @Override
    public void removeLocationUpdates(PendingIntent pendingIntent) {

    }

    //app life cycle


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


    @SuppressWarnings({"MissingPermission"})
    @Override
    public void onStop() {
        super.onStop();

        if (locationEngine !=null){
            locationEngine.removeLocationUpdates((LocationEngineCallback<com.mapbox.android.core.location.LocationEngineResult>) LocationEngineResult.getLastLocation());
        }
        if (locationLayerPlugin != null){
            locationLayerPlugin.onStop();
        }

        dMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationEngine != null){
           // locationEngine.deactivate();
        }
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
    @Override
    @SuppressWarnings("MissingPermission")
    public void onStart() {
        super.onStart();
        if (locationEngine != null){
           // locationEngine.requestLocationUpdates();
            System.out.println("location engine");
        }
        if (locationLayerPlugin != null){
            System.out.println("location engine");
        }
        dMapView.onStart();
    }


}
