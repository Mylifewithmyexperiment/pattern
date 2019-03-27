package com.elitecorelib.core.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.interfaces.OnLocationEnableListner;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;

/**
 * Using this class you can Enable and Disable Location Service
 * using default dialog. You dont want to go Location
 * Intent and Enable from Setting Screen.
 * <p>
 * Created by Chirag Parmar on 17-Oct-16.
 */
public class LocationServiceCheck {

    private final String MODULE = "LocationServiceCheck";
    private final int REQUEST_CHECK_SETTINGS = 9363 , PLAY_SERVICES_RESOLUTION_REQUEST = 6363;
    private Activity mActivity;
    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;
    private Status status;
    private OnLocationEnableListner mListner;
    private SharedPreferencesTask task = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private Context mContext;
    private  Dialog errorDialog;

    public LocationServiceCheck(Context activity) {
        mContext = activity;
    }

    public LocationServiceCheck(Activity activity, OnLocationEnableListner listner) {
        this.mActivity = activity;
        this.mListner = listner;

        googleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        createLocationRequest();
    }

    public boolean checkLocationEnable() {

        LocationManager mLocationManager = (LocationManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (gps_enabled && network_enabled) {
            EliteSession.eLog.d(MODULE, "Location Service Enable.");
            return true;
        } else {
            EliteSession.eLog.d(MODULE, "Location Service Disable.");
        }

        return false;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    public boolean isAboveMarshMellow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    public void isLocationServiceEnable() {

        if(checkPlayServices(mActivity)) {

            try {
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
                builder.setAlwaysShow(true); //this is the key ingredient
                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {

                        status = result.getStatus();

                        switch (status.getStatusCode()) {

                            case LocationSettingsStatusCodes.SUCCESS:
                                EliteSession.eLog.d(MODULE, "Location Service Enable.");
                                Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                                if (location != null) {
                                    ElitelibUtility.currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                    task.saveString(CoreConstants.CURRENT_LATITUDE, location.getLatitude() + "");
                                    task.saveString(CoreConstants.CURRENT_LONGITUDE, location.getLongitude() + "");
                                    //EliteSession.eLog.d(MODULE, location.toString());
                                }

                                mListner.isLocationEnable(true);

                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                // Location settings are not satisfied. But could be fixed by showing the user
                                // a dialog.
                                try {
                                    EliteSession.eLog.d(MODULE, "Location Resolution Required, Open Location Enable Dialog.");
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    status.startResolutionForResult(mActivity, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                    mListner.isLocationEnable(false);
                                    EliteSession.eLog.e(MODULE, "Open Location Dialog Error." + e.getMessage());
                                }

                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                // Location settings are not satisfied. However, we have no way to fix the
                                // settings so we won't show the dialog.
                                mListner.isLocationEnable(false);
                                break;

                                default:
                                    mListner.isLocationEnable(true);
                        }
                    }
                });
            }catch (Exception e) {
                EliteSession.eLog.e(MODULE, "dialog wrror." + e.getMessage());
            }
        }
    }

    /**
     * Location dialog request handle call. This method can compulsory call on onActivityResult on
     * Activity class otherwise you cant handle the callback method from the dialog.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        EliteSession.eLog.d(MODULE, "Location Dialog OnActivityResult Call");

        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        EliteSession.eLog.d(MODULE, "Location Enable Ok Click.");
                        mListner.isLocationEnable(true);

                        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        if (location != null) {
                            ElitelibUtility.currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            task.saveString(CoreConstants.CURRENT_LATITUDE, location.getLatitude() + "");
                            task.saveString(CoreConstants.CURRENT_LONGITUDE, location.getLongitude() + "");
                            //EliteSession.eLog.d(MODULE, location.toString());
                        }

                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        EliteSession.eLog.d(MODULE, "Location Enable Cancel Click.");
                        mListner.isLocationEnable(false);
                        break;
                    default:
                        break;
                }
                break;

            case PLAY_SERVICES_RESOLUTION_REQUEST:
                EliteSession.eLog.d(MODULE,"PLAY_SERVICES_RESOLUTION_REQUEST");
                errorDialog = null;

                break;
        }
    }

    private boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();

        EliteSession.eLog.d(MODULE,"Check Google play Service version");

        int result = googleAPI.isGooglePlayServicesAvailable(activity);
        if (result != ConnectionResult.SUCCESS) {

            EliteSession.eLog.d(MODULE,"Google Play service Version lower , Please update google play service version");
            if (googleAPI.isUserResolvableError(result)) {
                EliteSession.eLog.d(MODULE,"Google Play service Version lower , Error Dialog Show");

                if(errorDialog == null) {
                    EliteSession.eLog.d(MODULE, "Error Dialog null");
                    errorDialog = googleAPI.getErrorDialog(activity, result, PLAY_SERVICES_RESOLUTION_REQUEST, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            EliteSession.eLog.d(MODULE, "Google Play service Version lower , Error Dialog Cancel by user");
                            errorDialog = null;
                        }
                    });

                    if (!errorDialog.isShowing()) {
                        errorDialog.show();
                    }
                } else {
                    EliteSession.eLog.d(MODULE, "Error Dialog not null");
                }
            }
            return false;
        }
        return true;
    }
}
