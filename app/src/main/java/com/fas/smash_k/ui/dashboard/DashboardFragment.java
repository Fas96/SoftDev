package com.fas.smash_k.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.fas.smash_k.R;
import com.fas.smash_k.ui.home.HomeFragment;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
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
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.content.Context.LOCATION_SERVICE;
import static android.os.Looper.getMainLooper;

public class DashboardFragment extends Fragment implements OnMapReadyCallback, PermissionsListener, OnCameraTrackingChangedListener, LocationEngineListener,MapboxMap.OnMapClickListener {

    //main view of fragment
    private View mview;
    //the map view
    private MapView dMapView;
    //permission manager
    private PermissionsManager permissionsManager;
    //location Engine and Layer Plugin
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    // getting the original location
    private Location originLocation;
    //dialog when location allowing is denied
    Dialog MyDialoge;

    //location engine
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    //mapBox
    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;
    private boolean isInTrackingMode;
    private LocationEngineResult LocationEngineResult;

    //point clicked
    private Point originPosition;
    private Point destinationPosition;
    //marker
    private Marker destinationMarker;
    //button to start
    private Button start_nav_button;
    private DirectionsRoute currentRoute;

    //variables to route
    private NavigationMapRoute navigationMapRoute;
    private static final  String TAG ="DashboardActivity";



//    ///////////////////////////////////////////////////////////////
//    This is onCreateView for the DashboardFragment
//     inflates the view
//    =dMapView
//    creates savedInstanceState of mapview
//            sets click listener on start_nav_button
//    //////////////////////////////////////////////////////////////////
    @Override
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

        //getting the start nav button
        start_nav_button=mview.findViewById(R.id.nav_btn);
        start_nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // current Route needs to be checked
                DirectionsRoute route = currentRoute;
        // Create a NavigationLauncherOptions object to package everything together
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        //simulate
                        //.shouldSimulateRoute(true)
                        .directionsRoute(route)
                        .build();

        // Call this method with Context from within an Activity
                NavigationLauncher.startNavigation(getActivity(), options);

            }
        });

        return mview;
    }


    ///////////////////////////////////////////////////////////////
//    This is onMapReady from OnMapReadyCallback interface
//     instantiate mapbox
//
//    creates mapview Style mapview savedInstanceState
//            sets click listener on mapboxMap
//    //////////////////////////////////////////////////////////////////

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        //enableLocation();
        mapboxMap.setStyle(Style.MAPBOX_STREETS, this::enableLocationComponent);

    }

/*    //tried
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
    }*/

/*  ////////////////////////////////////////////////////////////////
  getLastLocation()
    is deprecated in Mapbox this method is to help get last location
    But this method is not tested correct yet
    ////////////////////////////////////////////////////////////////*/

    public Location getOriginLocation() {
        return originLocation;
    }

    @SuppressWarnings({"StatementWithEmptyBody", "MissingPermission"})
    public Location getLastLocation() {
        return getOriginLocation();
    }

/*    @SuppressWarnings({"StatementWithEmptyBody", "MissingPermission"})
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

    }*/

/*    private void initializeLocationLayer() {
        locationLayerPlugin = new LocationLayerPlugin(dMapView, mapboxMap, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode.NORMAL);
    }*/


  /*  ////////////////////////////////////////////////////////////
    This setCameraPosition for the currently clicked location
            it talkes the latitude and longitute for camera
    ////////////////////////////////////////////////////////////*/
    private void setCameraPosition(Location cameraPosition) {
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cameraPosition.getLatitude(), cameraPosition.getLongitude()), 13.0));
    }

    /*  ////////////////////////////////////////////////////////////
    Deprecated onConnected
     onLocationChanged
     onStatusChanged
     onProviderEnabled
     onProviderDisabled
  ////////////////////////////////////////////////////////////*/
    @Deprecated
    @SuppressWarnings({"MissingPermission", "StatementWithEmptyBody"})
    public void onConnected() {

        locationEngine.removeLocationUpdates((LocationEngineCallback<com.mapbox.android.core.location.LocationEngineResult>) getLastLocation());
        LocationListener myLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                originLocation=location;
//                if (location != null) {
//                    originLocation = location;
//                    setCameraPosition(location);
//                }
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
           // locationComponent.addOnLocationClickListener(this);

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

//    @SuppressWarnings({"MissingPermission"})
//    @Override
//    public void onLocationComponentClick() {
//        System.out.println("Fas: 1");
//        if (locationComponent.getLastKnownLocation() != null) {
//
//        }
//    }

    @Override
    public void onCameraTrackingDismissed() {
        isInTrackingMode = true;
    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {
// Empty on purpose
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//
//    onExplanationNeeded brings the explanation dialog to user
//    explaining why the location permission is needed for the app
//////////////////////////////////////////////////////////////////
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
//    @SuppressWarnings( {"MissingPermission"})
//    private void initLocationEngine() {
//        locationEngine = new LocationEngine() {
//            @Override
//            public void getLastLocation(@NonNull LocationEngineCallback<com.mapbox.android.core.location.LocationEngineResult> callback) throws SecurityException {
//
//            }
//
//            @Override
//            public void requestLocationUpdates(@NonNull LocationEngineRequest request, @NonNull LocationEngineCallback<com.mapbox.android.core.location.LocationEngineResult> callback, @Nullable Looper looper) throws SecurityException {
//
//            }
//
//            @Override
//            public void requestLocationUpdates(@NonNull LocationEngineRequest request, PendingIntent pendingIntent) throws SecurityException {
//
//            }
//
//            @Override
//            public void removeLocationUpdates(@NonNull LocationEngineCallback<com.mapbox.android.core.location.LocationEngineResult> callback) {
//
//            }
//
//            @Override
//            public void removeLocationUpdates(PendingIntent pendingIntent) {
//
//            }
//        };
//    }
@Override
public boolean onMapClick(@NonNull LatLng point) {
    //remove markers
    if(destinationMarker!=null){
        mapboxMap.removeMarker(destinationMarker);
    }
    //Toast.makeText(getContext(),"CLicked",Toast.LENGTH_SHORT).show();
    destinationMarker =mapboxMap.addMarker(new MarkerOptions().position(point));

    destinationPosition=Point.fromLngLat(point.getLongitude(),point.getLatitude());
    originPosition=Point.fromLngLat(getLastKnownLocation(getContext()).getLongitude(),getLastKnownLocation(getContext()).getLatitude());

    System.out.println("Fas : "+destinationPosition.toString());
    System.out.println("Fas : "+originPosition.toString());

    getRoute(originPosition,destinationPosition);
    start_nav_button.setEnabled(true);


    return true;
}

    //this method is to get the route
    private void  getRoute(Point origin, Point destination){
        NavigationRoute.builder(getContext())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body()==null){
                            Timber.e("No routes foind, check right user and access token");
                        }else if (response.body().routes().size()==0){
                            Timber.e("FASNo routes found");
                        }
                        currentRoute = response.body().routes().get(0);
                        if(navigationMapRoute!=null){
                            navigationMapRoute.removeRoute();
                        }else {
                            navigationMapRoute= new NavigationMapRoute(null,dMapView,mapboxMap);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Timber.e("FAS%s", t.getMessage());

                    }
                });
    }
    public static Location getLastKnownLocation(Context context) {
        try {
            LocationManager mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            List<String> providers = mLocationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
            return bestLocation;
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) { mapboxMap.getStyle((this::enableLocationComponent));
        } else { Toast.makeText(getActivity(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show(); }
    }



    //app life cycle


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        dMapView.onSaveInstanceState(outState);
    }


    @Override
    @SuppressWarnings("MissingPermission")
    public void onStart() {
        super.onStart();
        if (locationEngine != null){
           // locationEngine.requestLocationUpdates();
            System.out.println("location engine");
            locationEngine.removeLocationUpdates((LocationEngineCallback<com.mapbox.android.core.location.LocationEngineResult>) LocationEngineResult.getLastLocation());

        }
        if (locationLayerPlugin != null){
            System.out.println("location engine");
            locationLayerPlugin.onStart();
        }
        dMapView.onStart();
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
    public void onDestroy() {
        super.onDestroy();
        if (locationEngine != null){
            // locationEngine.deactivate()
        }
        dMapView.onDestroy();

    }

}
