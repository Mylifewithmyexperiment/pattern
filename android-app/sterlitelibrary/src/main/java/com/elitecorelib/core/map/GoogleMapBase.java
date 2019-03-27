package com.elitecorelib.core.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elitecore.eliteconnectlibrary.R;
import com.elitecore.wifi.listener.OnInternetCheckCompleteListner;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.interfaces.OnLocationEnableListner;
import com.elitecorelib.core.interfaces.OnPermissionListner;
import com.elitecorelib.core.pojo.PojoClusterItem;
import com.elitecorelib.core.pojo.PojoLocation;
import com.elitecorelib.core.services.InterNetAvailabilityCheckTask;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.GMapV2Direction;
import com.elitecorelib.core.utility.LocationServiceCheck;
import com.elitecorelib.core.utility.PermissionClass;
import com.elitecorelib.core.utility.PermissionConstant;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;

/**
 * Created by Chirag Parmar on 07/03/18.
 */

public class GoogleMapBase extends AppCompatActivity implements OnInternetCheckCompleteListner, OnLocationEnableListner, OnPermissionListner, OnMapReadyCallback {

    private final String MODULE = "GoogleMapBase";
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 963;
    private final int INTERNET_CODE = 100;
    public ProgressBar mapProgressBar;
    private LocationServiceCheck mLocationManager;
    private PermissionClass permissionClass;
    private View mapView;
    private boolean isLocationEnable;
    private GoogleMap mMap;
    private ClusterManager<PojoClusterItem> mClusterManager;
    private ArrayList<PojoLocation> mhotspotList;
    private ArrayList<Integer> mIconList;
    private boolean isLocationWiseIcon;
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private TextView tv_Map_NoInternet;
    private ViewGroup viewGroup;
    private LatLng currentLocation;
    private boolean isClusterMap = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        EliteSession.eLog.d(MODULE, "Call GoogleMapBase Class");
        mLocationManager = new LocationServiceCheck(this, this);
        permissionClass = new PermissionClass(this, this);
        permissionClass.isPermissionGrant(PermissionConstant.PERMISSION_LOCATION, getString(R.string.location_permission_message), getString(R.string.location_permission_message_rational));
        tv_Map_NoInternet = (TextView) findViewById(R.id.tv_noInternet_message);
        mapProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mapProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void isPermissionGranted(boolean isGrandted, String permission) {

        if (permission.equals(PermissionConstant.PERMISSION_LOCATION)) {
            if (isGrandted) {
                EliteSession.eLog.i(MODULE, "Location Service Enable");
                mLocationManager.isLocationServiceEnable();

            } else {
                EliteSession.eLog.i(MODULE, "Location Service Disable");
                mapLoad();
            }
        }
    }

    @Override
    public void isLocationEnable(boolean enable) {
        EliteSession.eLog.d(MODULE, "User Current Location is " + enable);
        isLocationEnable = enable;
        mapLoad();
    }

    @Override
    protected void onResume() {
        new InterNetAvailabilityCheckTask(this, CoreConstants.INTERNET_CHECK_URL).execute();
        super.onResume();

        if (spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE) != null && !spTask.getString(SharedPreferencesConstant.CURRENT_LONGITUDE).equals("")) {
            currentLocation = new LatLng(Double.parseDouble(spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE)), Double.parseDouble(spTask.getString(SharedPreferencesConstant.CURRENT_LONGITUDE)));
        }
    }

    private void mapLoad() {

        EliteSession.eLog.d(MODULE, "initializeMap");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (isLocationEnable)
            mMap.setMyLocationEnabled(true);

        setMyLocationButton();

        if (spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE) != null && !spTask.getString(SharedPreferencesConstant.CURRENT_LONGITUDE).equals("")) {

            currentLocation = new LatLng(Double.parseDouble(spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE)), Double.parseDouble(spTask.getString(SharedPreferencesConstant.CURRENT_LONGITUDE)));
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(currentLocation)
                    .zoom(10)
                    .bearing(30)
                    .tilt(45)
                    .build()));
        }

        EliteSession.eLog.d(MODULE, "Map Load");

        Intent intent = new Intent(CoreConstants.MAP_RECIEVER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    protected GoogleMap getMap() {
        return mMap;
    }

    protected ProgressBar getMapProgressBar() {
        return mapProgressBar;
    }

    protected void zoomOnLocation(double mSelectLat, double mSelectLang) {

        mMap.clear();
        currentLocation = new LatLng(mSelectLat, mSelectLang);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(currentLocation)
                .zoom(18)
                .bearing(30)
                .tilt(45)
                .build()));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMapasPerType();
            }
        });
    }

    /**
     * Set MyLocation Button in Google map. You can change position of the button.
     */
    private void setMyLocationButton() {

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLocationManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                EliteSession.eLog.i(MODULE, "Place: " + place.getName());

                mMap.clear();
                LatLng search_location = place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(search_location).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).draggable(false).title(place.getName().toString()));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                        .target(search_location)
                        .zoom(15)
                        .bearing(30)
                        .tilt(45)
                        .build()));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMapasPerType();
                    }
                });

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                EliteSession.eLog.i(MODULE, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                EliteSession.eLog.i(MODULE, "Google Map result Cancel");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionClass.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void showClusterHotspot(final ArrayList<PojoLocation> mLocationList, ArrayList<Integer> markerIcon, boolean isLocationWiseIcon) {

        isClusterMap = true;

        if (mLocationList != null && mLocationList.size() > 0) {
            tv_Map_NoInternet.setVisibility(View.GONE);

            mhotspotList = mLocationList;
            mIconList = markerIcon;
            this.isLocationWiseIcon = isLocationWiseIcon;

            EliteSession.eLog.i(MODULE, "Wi-Fi Hotspot Available.");

            // Initialize the manager with the context and the map.
            // (Activity extends context, so we can pass 'this' in the constructor.)
            mClusterManager = new ClusterManager<PojoClusterItem>(this, mMap);

            // Point the map's listeners at the listeners implemented by the cluster
            // manager.

            mMap.setOnCameraIdleListener(mClusterManager);
            mMap.setOnMarkerClickListener(mClusterManager);
            mMap.setOnInfoWindowClickListener(mClusterManager);


            for (int i = 0; i < mLocationList.size(); i++) {

                PojoLocation pojoLocation = mLocationList.get(i);
                LatLng location = new LatLng(Double.valueOf(pojoLocation.getLatitude()), Double.valueOf(pojoLocation.getLongitude()));

                PojoClusterItem offsetItem;

                if (isLocationWiseIcon && mLocationList.size() == markerIcon.size()) {
                    offsetItem = new PojoClusterItem(location.latitude, location.longitude, pojoLocation.getLocationName(), markerIcon.get(i));
                } else {
                    offsetItem = new PojoClusterItem(location.latitude, location.longitude, pojoLocation.getLocationName(), 0);
                }
                mClusterManager.addItem(offsetItem);
            }

            mClusterManager.cluster();
            mClusterManager.setRenderer(new HotspotRender(this, mMap, mClusterManager));
            mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<PojoClusterItem>() {
                @Override
                public boolean onClusterItemClick(final PojoClusterItem pojoClusterItem) {

                    if (pojoClusterItem != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                        .target(new LatLng(pojoClusterItem.getPosition().latitude, pojoClusterItem.getPosition().longitude))
                                        .zoom(15)
                                        .bearing(30)
                                        .tilt(45)
                                        .build()));
                            }
                        });
                        String clickSnippet = pojoClusterItem.getSnippet();
                        if (mLocationList != null && mLocationList.size() > 0) {

                            for (final PojoLocation location : mLocationList) {

                                if (location.getLocationName().equals(clickSnippet)) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showHostPostDetailDialog(location);
                                        }
                                    });
                                    break;
                                }
                            }
                        }
                    }


                    return true;
                }
            });
        } else {
            EliteSession.eLog.i(MODULE, "Wi-Fi Hotspot not Available.");
        }
    }

    @Override
    public void isInterNetAvailable(int requestId, final String status, String json) {

        if (status.equals(CoreConstants.INTERNET_CHECK_SUCCESS)) {
            tv_Map_NoInternet.setVisibility(View.GONE);
        } else if (status.equals(CoreConstants.INTERNET_CHECK_FAIL)) {
            tv_Map_NoInternet.setVisibility(View.VISIBLE);
        }
    }

    private void showHostPostDetailDialog(final PojoLocation hostpostDetail) {

        EliteSession.eLog.d(MODULE, hostpostDetail.getLocationName() + " marker click");
        StringBuilder builder = new StringBuilder();
        builder.append(hostpostDetail.getCategory());
        builder.append("\n\n");
        builder.append(hostpostDetail.getLocationDescription());

        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light));
        alertbuilder.setMessage(builder.toString());
        alertbuilder.setTitle(hostpostDetail.getLocationName().toUpperCase());
        alertbuilder.setPositiveButton(getString(R.string.direction), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                LatLng destinationLocation = new LatLng(hostpostDetail.getLatitude(), hostpostDetail.getLongitude());
                try {
                    LatLng currentLocationTemp = null;
                    if (spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE) != null && !spTask.getString(SharedPreferencesConstant.CURRENT_LONGITUDE).equals("")) {
                        currentLocationTemp = new LatLng(Double.parseDouble(spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE)), Double.parseDouble(spTask.getString(SharedPreferencesConstant.CURRENT_LONGITUDE)));
                    }
                    if (currentLocationTemp != null) {
                        mMap.clear();
                        ElitelibUtility.showRoutDirection(GoogleMapBase.this, mMap, currentLocationTemp, destinationLocation, GMapV2Direction.MODE_WALKING);

//                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                        builder.include(currentLocationTemp);
//                        builder.include(destinationLocation);
//
//                        LatLngBounds bounds = builder.build();
//
//                        CameraUpdate cUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 1000);
//
//                        if(mMap != null)
//                            mMap.animateCamera(cUpdate);

                        showMapasPerType();
                    }
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, "Error location not fetch proper " + e);
                }
            }
        });
        alertbuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        alertbuilder.create();
        alertbuilder.show();
    }

    protected void googleSearch() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    protected void showHotspot(final ArrayList<PojoLocation> mLocationList, ArrayList<Integer> markerIcon, boolean isLocationWiseIcon) {

        isClusterMap = false;

        if (mLocationList != null && mLocationList.size() > 0) {
            tv_Map_NoInternet.setVisibility(View.GONE);
            mhotspotList = mLocationList;
            mIconList = markerIcon;
            this.isLocationWiseIcon = isLocationWiseIcon;

            for (int i = 0; i < mLocationList.size(); i++) {

                PojoLocation pojoLocation = mLocationList.get(i);
                LatLng location = new LatLng(Double.valueOf(pojoLocation.getLatitude()), Double.valueOf(pojoLocation.getLongitude()));


                BitmapDescriptor localBitmapDescriptor;
                if (isLocationWiseIcon && mLocationList.size() == markerIcon.size()) {
                    localBitmapDescriptor = BitmapDescriptorFactory.fromResource(markerIcon.get(i));
                } else {
                    localBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi);
                }

                mMap.addMarker(new MarkerOptions().position(location).icon(localBitmapDescriptor).draggable(false).snippet(pojoLocation.getLocationName()));
            }

            hostpostClick();
        }
    }

    private void hostpostClick() {

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)));
                    String clickSnippet = marker.getSnippet();
                    if (mhotspotList != null && mhotspotList.size() > 0) {

                        for (final PojoLocation location : mhotspotList) {

                            if (location.getLocationName().equals(clickSnippet)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showHostPostDetailDialog(location);
                                    }
                                });
                                break;
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    private void showMapasPerType() {

        if (isClusterMap)
            showClusterHotspot(mhotspotList, mIconList, isLocationWiseIcon);
        else
            showHotspot(mhotspotList, mIconList, isLocationWiseIcon);
    }

    protected void showHotspotNotAvilable(String message) {
        EliteSession.eLog.d(MODULE, "Hotspot not available");
        tv_Map_NoInternet.setText(message);
        tv_Map_NoInternet.setVisibility(View.VISIBLE);
    }

    private class HotspotRender extends DefaultClusterRenderer<PojoClusterItem> {

        HotspotRender(Context context, GoogleMap map, ClusterManager<PojoClusterItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 3;
        }

        @Override
        protected void onBeforeClusterItemRendered(PojoClusterItem item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);
            BitmapDescriptor descriptor;

            if (item.getmIcon() == 0) {
                descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi);
            } else {
                descriptor = BitmapDescriptorFactory.fromResource(item.getmIcon());
            }
            markerOptions.position(item.getPosition()).icon(descriptor).draggable(false).snippet(item.getTitle());
        }
    }
}
