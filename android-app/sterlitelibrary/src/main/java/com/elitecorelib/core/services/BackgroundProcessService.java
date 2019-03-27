package com.elitecorelib.core.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.elitecorelib.andsf.utility.ANDSFUtility;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.android.gms.maps.model.LatLng;

public class BackgroundProcessService extends Service {
    private static final String TAG = "BackgroundProcessService";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    private Location location;
    private SharedPreferencesTask task = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();


    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation.set(location);
            callLatLong();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EliteSession.eLog.d(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        EliteSession.eLog.d(TAG, "onCreate");
        callLatLong();
    }

    public void callLatLong() {
        initializeLocationManager();
        try {
            isGPSEnable = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnable = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnable && !isNetworkEnable) {

            } else {
                if (isNetworkEnable) {
                    location = null;
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                            mLocationListeners[1]);
                    if (mLocationManager != null) {
                        location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            task.saveString(SharedPreferencesConstant.CURRENT_LATITUDE, location.getLatitude() + "");
                            task.saveString(SharedPreferencesConstant.CURRENT_LONGITUDE, location.getLongitude() + "");

                            ANDSFUtility.getDeviceDetails(this, task.getString(SharedPreferencesConstant.CURRENT_LATITUDE), task.getString(SharedPreferencesConstant.CURRENT_LONGITUDE));

                        }
                    }
                } else if (isGPSEnable) {
                    location = null;
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                            mLocationListeners[0]);
                    if (mLocationManager != null) {
                        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            task.saveString(SharedPreferencesConstant.CURRENT_LATITUDE, location.getLatitude() + "");
                            task.saveString(SharedPreferencesConstant.CURRENT_LONGITUDE, location.getLongitude() + "");

                            ANDSFUtility.getDeviceDetails(this, task.getString(SharedPreferencesConstant.CURRENT_LATITUDE), task.getString(SharedPreferencesConstant.CURRENT_LONGITUDE));

                        }
                    }
                }
            }
            EliteSession.eLog.d(TAG, "Device Latitude : " + task.getString(SharedPreferencesConstant.CURRENT_LATITUDE) + " Longitude : " + task.getString(SharedPreferencesConstant.CURRENT_LONGITUDE));


        } catch (SecurityException ex) {
            EliteSession.eLog.i(TAG, "fail to request location update, ignore " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            EliteSession.eLog.d(TAG, "network or gps provider does not exist, " + ex.getMessage());
        }

    }

    @Override
    public void onDestroy() {
        EliteSession.eLog.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    EliteSession.eLog.i(TAG, "fail to remove location listners, ignore " + ex.getMessage());
                }
            }
        }
    }

    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}