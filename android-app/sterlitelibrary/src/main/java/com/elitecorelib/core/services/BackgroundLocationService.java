package com.elitecorelib.core.services;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.dao.DBOperation;
import com.elitecorelib.core.fcm.NotificationClass;
import com.elitecorelib.core.pojo.PojoLocation;
import com.elitecorelib.core.pojo.PojoServiceResponseNotification;
import com.elitecorelib.core.realm.RealmOperation;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.elitecorelib.core.CoreConstants.ADVERTISEMENT_LANGUAGE;
import static com.elitecorelib.core.CoreConstants.APPLANGUAGE;
import static com.elitecorelib.core.CoreConstants.COMMUNICATIONMODE;
import static com.elitecorelib.core.CoreConstants.DATABASE_TYPE;
import static com.elitecorelib.core.CoreConstants.DEVICEID;
import static com.elitecorelib.core.CoreConstants.FASTEST_INTERVAL;
import static com.elitecorelib.core.CoreConstants.LOCATIONBASENOTIFICATION;
import static com.elitecorelib.core.CoreConstants.LOCATIONID;
import static com.elitecorelib.core.CoreConstants.MESSAGETYPE;
import static com.elitecorelib.core.CoreConstants.MESSAGETYPEVALUE;
import static com.elitecorelib.core.CoreConstants.NFCALLBACKINTERVAL_VALUE;
import static com.elitecorelib.core.CoreConstants.NFCALLBACKMODE;
import static com.elitecorelib.core.CoreConstants.NFCALLBACK_TIMEMODE_VALUE;
import static com.elitecorelib.core.CoreConstants.NOTIFICATIONTRIGGERRESET;
import static com.elitecorelib.core.CoreConstants.NOTIFICATIONTRIGGERRESET_VALUE;
import static com.elitecorelib.core.CoreConstants.NUMBEROFNOTIFICATIONFORSAMELOCATION_VALUE;
import static com.elitecorelib.core.CoreConstants.REALM;
import static com.elitecorelib.core.CoreConstants.UPDATE_INTERVAL;
import static com.elitecorelib.core.CoreConstants.WS_COMMUNICATION;
import static com.elitecorelib.core.CoreConstants.imei;
import static com.elitecorelib.core.CoreConstants.imsi;
import static com.elitecorelib.core.CoreConstants.operatingSystem;
import static com.elitecorelib.core.CoreConstants.operatingSystemValue;
import static com.elitecorelib.core.dao.DBQueryFieldConstants.REGISTEREDDEVICEID;
import static com.elitecorelib.core.utility.ElitelibUtility.currentLatLong;
import static com.elitecorelib.core.utility.ElitelibUtility.getDistanceBetweenLocations;
import static com.elitecorelib.core.utility.ElitelibUtility.getKeyPairvalue;


/**
 * BackgroundLocationService used for tracking user location in the background.
 *
 * @author
 */
public class BackgroundLocationService extends IntentService implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private final static String MODULE = "BackgroundLocationService";
    public static boolean startDistanceCheck;
    private int current;
    private IBinder mBinder = new LocalBinder();
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Boolean servicesAvailable = false;
    private Location location;
    private boolean isNotificaitonTrigger = false;
    private SharedPreferencesTask task = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private Context context = LibraryApplication.getLibraryApplication().getLibraryContext();

    public BackgroundLocationService() {
        super("");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        servicesAvailable = servicesConnected();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean servicesConnected() {
        try {
            // Check that Google Play services is available
            int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            // If Google Play services is available
            if (ConnectionResult.SUCCESS == resultCode) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while Checking Google Services");
            return false;

        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        setUpLocationClientIfNeeded();
        mGoogleApiClient.connect();
        return START_STICKY;
    }

    /*
     * Create a new location client, using the enclosing class to
     * handle callbacks.
     */
    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
    }

    // Define the callback method that receives location updates
    @Override
    public void onLocationChanged(Location location) {
        // Report to the UI that the location was updated
        String msg = Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //EliteSession.eLog.d(MODULE, msg);
        task.saveString(SharedPreferencesConstant.CURRENT_LATITUDE, location.getLatitude() + "");
        task.saveString(SharedPreferencesConstant.CURRENT_LONGITUDE, location.getLongitude() + "");

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        if (servicesAvailable && mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            // Destroy the current location client
            mGoogleApiClient = null;
        }
        super.onDestroy();
    }

    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle bundle) {
        // Request location updates using static settings

        try {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location != null) {
                currentLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                task.saveString(SharedPreferencesConstant.CURRENT_LATITUDE, location.getLatitude() + "");
                task.saveString(SharedPreferencesConstant.CURRENT_LONGITUDE, location.getLongitude() + "");
                //EliteSession.eLog.d(MODULE, location.toString());
            }
            if (location != null && startDistanceCheck) {
                if (LibraryApplication.getLibraryApplication().getLocationToCheckDistanceWith() == null) {
                    LibraryApplication.getLibraryApplication().setLocationToCheckDistanceWith(location);
                    //first to check near by wifi then it will check on distance base
                    //callNCheckLocationService();
                }
                if (task.getString(NFCALLBACKMODE).equals(NFCALLBACK_TIMEMODE_VALUE)) {
                    callNCheckLocationService();
                } else {
                    if (location != null) {
                        currentLatLong = new LatLng(location.getLatitude(), location.getLongitude());

                        Location locationToCheckDistanceWith = LibraryApplication.getLibraryApplication().getLocationToCheckDistanceWith();

                        if (task.getString(NFCALLBACKMODE).equals(CoreConstants.NFCALLBACK_LOCATIONMODE_VALUE) && locationToCheckDistanceWith != null) {
                            double distance = getDistanceBetweenLocations(location.getLatitude(), location.getLongitude(), locationToCheckDistanceWith.getLatitude(), locationToCheckDistanceWith.getLongitude(), 'M');
                            int locationCallDistance = 0;
                            try {
                                locationCallDistance = NFCALLBACKINTERVAL_VALUE;
                            } catch (Exception e) {
                                EliteSession.eLog.d(MODULE, "locationCallDistance not set properly in config file or from server");
                                locationCallDistance = 60;
                            }
                            if (distance >= locationCallDistance) {
                                LibraryApplication.getLibraryApplication().setLocationToCheckDistanceWith(location);
                                callNCheckLocationService();
                            }
                        }
                    }
                }
            } else {
                EliteSession.eLog.d(MODULE, "getLocation Not Found or nearyby location service yet not invoked");
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
        onDisconnected();
    }

    public void onDisconnected() {
        mGoogleApiClient = null;
        EliteSession.eLog.e(MODULE, "Service disconnected");
    }
    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {

            // If no resolution is available, display an error dialog
        } else {

        }
    }

    private void callNFThroghtGCMOrServer(String msg) {
        EliteSession.eLog.d(MODULE, "generating notification from server message.");
        NotificationClass notiClass = new NotificationClass(this);

        try {
            EliteSession.eLog.i(MODULE, " Message returned from server for notification policy is");
            EliteSession.eLog.i(MODULE, msg);
            if (task.getInt(LOCATIONBASENOTIFICATION) == 1) {
                Gson gson = new Gson();
                PojoServiceResponseNotification resObj = gson.fromJson(msg, PojoServiceResponseNotification.class);
                if (resObj.getResponseCode() == 1 && task.getString(COMMUNICATIONMODE).equals(String.valueOf(WS_COMMUNICATION))) {

                    if (resObj.getResponseMessage() != null && resObj.getResponseData() != null) {
                        EliteSession.eLog.i(MODULE, "Notification Message Notified to user is : " + resObj.getResponseData().getMessage());

                        notiClass.showNotification(resObj.getResponseData().getMessage());
                        callAutoConnectService();
                        if (location != null) {
                            int currentCount = task.getInt(SharedPreferencesConstant.NOTIFICAITIONDISPLAYEINSAMELOCATION);
                            currentCount++;
                            task.saveInt(SharedPreferencesConstant.NOTIFICAITIONDISPLAYEINSAMELOCATION, currentCount);
                        }
                    }
                } else {
                    callAutoConnectService();
                    EliteSession.eLog.i(MODULE, resObj.getResponseMessage());
                }
            } else if (task.getInt(LOCATIONBASENOTIFICATION) == 0) {
                notiClass.showNotification(msg);
                callAutoConnectService();

                if (location != null) {
                    int currentCount = task.getInt(SharedPreferencesConstant.NOTIFICAITIONDISPLAYEINSAMELOCATION);
                    currentCount++;
                    task.saveInt(SharedPreferencesConstant.NOTIFICAITIONDISPLAYEINSAMELOCATION, currentCount);
                }
            } else {

                Gson gson = new Gson();
                PojoServiceResponseNotification resObj = gson.fromJson(msg, PojoServiceResponseNotification.class);
                if (resObj.getResponseCode() == 1 && task.getString(COMMUNICATIONMODE).equals(WS_COMMUNICATION + "")) {
                    if (resObj.getResponseMessage() != null && resObj.getResponseData() != null) {
                        EliteSession.eLog.i(MODULE, "Notification Message Notified to user is : " + resObj.getResponseData().getMessage());
                        notiClass.showNotification(resObj.getResponseData().getMessage());
                        callAutoConnectService();
                        if (location != null) {
                            int currentCount = task.getInt(SharedPreferencesConstant.NOTIFICAITIONDISPLAYEINSAMELOCATION);
                            currentCount++;
                            task.saveInt(SharedPreferencesConstant.NOTIFICAITIONDISPLAYEINSAMELOCATION, currentCount);
                        }
                    }
                } else {
                    callAutoConnectService();
                    EliteSession.eLog.i(MODULE, resObj.getResponseMessage());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            EliteSession.eLog.e(MODULE, "Error is - " + e.getMessage());
        }
    }

    private void callNCheckLocationService() {
        //Not to call Notification service while update location is in process.
        //		if(Constants.isLocationUpdating)
        //		{
        //			EliteSession.eLog.d(MODULE, "Location update is under process, not to call notification service.");
        //			return;
        //		}
        if (task.getString(imei) != null && task.getString(imsi) != null) {
            try {

                ArrayList<PojoLocation> locationList;

                if (task.getString(DATABASE_TYPE).equals(REALM)) {
                    locationList = RealmOperation.with(LibraryApplication.getLibraryApplication().getLibraryContext()).getAllHotspot();
                } else {
                    locationList = DBOperation.getDBHelperOperation().getNearHotspotLocation();
                }

                if (locationList != null && locationList.size() > 0) {

                    int locationSize = locationList.size();

                    EliteSession.eLog.d(MODULE, "Checking near by hotspot/location with " + locationSize + " Data");

                    for (int i = 0; i < locationSize; i++) {

                        try {

                            final PojoLocation pojoLocation = locationList.get(i);
                            double distance = getDistanceBetweenLocations(location.getLatitude(), location.getLongitude(), pojoLocation.getLatitude(), pojoLocation.getLongitude(), 'M');

//                            task.saveBoolean(SharedPreferencesConstant.USER_IN_LOCATION, true);

                            if (distance < pojoLocation.getRadius()) {

                                EliteSession.eLog.d(MODULE, "getLocation Found Location Calling,sendNotificationURL Service, distance is " + distance);
                                task.saveString(SharedPreferencesConstant.LASTNEARBYHOTSPOTLOCATION, pojoLocation.getLocationName());

                                // For the leave policy we can set this two params
                                task.saveBoolean(SharedPreferencesConstant.USER_IN_LOCATION, true);
                                task.saveLong(SharedPreferencesConstant.USER_IN_LOCATION_ID, pojoLocation.getLocationId());

                                if (task.getString(DEVICEID) != null) {

                                    final JSONObject jsonObject = new JSONObject();
                                    jsonObject.put(REGISTEREDDEVICEID, task.getString(DEVICEID));
                                    jsonObject.put(LOCATIONID, pojoLocation.getLocationId());


                                    if (task.getLong(SharedPreferencesConstant.LASTKNOWLOCATIONID) != 0) {

                                        if (pojoLocation.getLocationId() != task.getLong(SharedPreferencesConstant.LASTKNOWLOCATIONID)) {
                                            task.saveInt(SharedPreferencesConstant.NOTIFICAITIONDISPLAYEINSAMELOCATION, 0);
                                            isNotificaitonTrigger = true;
                                            task.saveLong(SharedPreferencesConstant.NOTIFICATIONTRIGGERTIME, System.currentTimeMillis());
                                            EliteSession.eLog.d(MODULE, "Enter Different location, Trigger LocationBase notificaiton");
                                        } else {
                                            EliteSession.eLog.d(MODULE, "Enter same location");

                                            if (task.getInt(LOCATIONBASENOTIFICATION) == 0) {
                                                if (System.currentTimeMillis() > (task.getLong(SharedPreferencesConstant.NOTIFICATIONTRIGGERTIME) + Double.parseDouble(getKeyPairvalue(NOTIFICATIONTRIGGERRESET, NOTIFICATIONTRIGGERRESET_VALUE)))) {
                                                    isNotificaitonTrigger = true;
                                                    task.saveLong(SharedPreferencesConstant.NOTIFICATIONTRIGGERTIME, System.currentTimeMillis());
                                                    EliteSession.eLog.d(MODULE, "Enter same location, Notification Trigger after 24 hour");
                                                } else {
                                                    isNotificaitonTrigger = false;
                                                    EliteSession.eLog.d(MODULE, "Enter same location, Notification Not Trigger");
                                                }
                                            } else {
                                                isNotificaitonTrigger = true;
                                                EliteSession.eLog.d(MODULE, "In Online Mode isNotificaitonTrigger is always true");
                                            }
                                        }

                                    } else {
                                        EliteSession.eLog.d(MODULE, "New User, Trigger LocationBase notificaiton");
                                        isNotificaitonTrigger = true;
                                        task.saveLong(SharedPreferencesConstant.NOTIFICATIONTRIGGERTIME, System.currentTimeMillis());
                                    }
                                    int toDisplay = 0;
                                    try {
                                        toDisplay = NUMBEROFNOTIFICATIONFORSAMELOCATION_VALUE;
                                        current = task.getInt(SharedPreferencesConstant.NOTIFICAITIONDISPLAYEINSAMELOCATION);
                                        EliteSession.eLog.d(MODULE, "current " + current);
                                        if (toDisplay == current) {
                                            //limit exceed for the current location, set in property file don't process for notification
                                            //Now this is getting maintained from the server side, so putting comment.
                                            //return;
                                        }

                                    } catch (Exception e) {
                                        EliteSession.eLog.d(MODULE, "Number format exception while geting number notification to display");
                                    }
                                    task.saveLong(SharedPreferencesConstant.LASTKNOWLOCATIONID, pojoLocation.getLocationId());
                                    jsonObject.put(imei, task.getString(imei));
                                    jsonObject.put(imsi, task.getString(imsi));
                                    jsonObject.put(operatingSystem, operatingSystemValue);
                                    jsonObject.put(CoreConstants.USERIDENTITY, task.getString(CoreConstants.USERIDENTITY));
                                    jsonObject.put(APPLANGUAGE, task.getString(ADVERTISEMENT_LANGUAGE));

                                    if (task.getString(CoreConstants.SECRETKEY) != null) {
                                        jsonObject.put(CoreConstants.SECRETKEY, task.getString(CoreConstants.SECRETKEY));
                                    }

                                    if (task.getBoolean(CoreConstants.isAlreadyLogin)) {

                                    final String localMessage = pojoLocation.getOfflineMessage();

                                        Runnable locationRunnable = new Runnable() {

                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                String msg = "";
                                                try {

                                                    if (isNotificaitonTrigger) {

                                                        if (task.getInt(LOCATIONBASENOTIFICATION) == 0) {

                                                            if ( localMessage!= null && !localMessage.equals("")) {

                                                                msg = localMessage;
                                                            } else {
                                                                EliteSession.eLog.d(MODULE, "Offline message not available for this Location");
                                                            }
                                                        } else if (task.getInt(LOCATIONBASENOTIFICATION) == 1) {

                                                            ConnectionManagerTaskNew connectionTask = new ConnectionManagerTaskNew(null, CoreConstants.MONETIZATION_SENDNOTIFICATIONBYLOCATION_REQUESTID);
                                                            msg = connectionTask.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_SENDNOTIFICATIONBYLOCATION).get();

//                                                            if (msg == null || msg.equals("")) {
//                                                                if (localMessage != null && !localMessage.equals("")) {
//                                                                    msg = localMessage;
//                                                                } else {
//                                                                    EliteSession.eLog.d(MODULE, "Offline message not available for this Location or Monetization not richable");
//                                                                }
//                                                            }

                                                        } else {
                                                            ConnectionManagerTaskNew connectionTask = new ConnectionManagerTaskNew(null, CoreConstants.MONETIZATION_SENDNOTIFICATIONBYLOCATION_REQUESTID);
                                                            msg = connectionTask.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_SENDNOTIFICATIONBYLOCATION).get();
                                                        }
                                                    }
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                } catch (ExecutionException e) {
                                                    e.printStackTrace();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                if (msg.compareTo("") != 0)
                                                    callNFThroghtGCMOrServer(msg);
                                            }
                                        };

                                        Thread tLoadLocation = new Thread(locationRunnable);
                                        tLoadLocation.start();
                                    }
                                } else {
                                    EliteSession.eLog.d(MODULE, "GCM reigstration id not found from application");
                                }
                                break; //once got the message no need to check remaining location distance.
                            } else {
                                task.saveString(SharedPreferencesConstant.LASTNEARBYHOTSPOTLOCATION, "");

                                // Leave Policy check
                                if (task.getBoolean(SharedPreferencesConstant.USER_IN_LOCATION)) {
                                    EliteSession.eLog.d(MODULE, "User avalialbe in hostpost Location");

                                    if (task.getLong(SharedPreferencesConstant.USER_IN_LOCATION_ID) == pojoLocation.getLocationId()) {
                                        EliteSession.eLog.d(MODULE, "User Leave the location which location is " + pojoLocation.getLocationName());

                                        final JSONObject jsonObject = new JSONObject();
                                        jsonObject.put(REGISTEREDDEVICEID, task.getString(DEVICEID));
                                        jsonObject.put(LOCATIONID, pojoLocation.getLocationId());
                                        jsonObject.put(imei, task.getString(imei));
                                        jsonObject.put(imsi, task.getString(imsi));
                                        jsonObject.put(operatingSystem, operatingSystemValue);
                                        jsonObject.put(CoreConstants.USERIDENTITY, task.getString(CoreConstants.USERIDENTITY));
                                        jsonObject.put(APPLANGUAGE, task.getString(ADVERTISEMENT_LANGUAGE));
                                        jsonObject.put(MESSAGETYPE, MESSAGETYPEVALUE);

                                        if (task.getString(CoreConstants.SECRETKEY) != null) {
                                            jsonObject.put(CoreConstants.SECRETKEY, task.getString(CoreConstants.SECRETKEY));
                                        }

                                        if (task.getBoolean(CoreConstants.isAlreadyLogin)) {
                                            Runnable locationRunnable = new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated method stub
                                                    String msg = "";
                                                    try {
                                                        if (task.getInt(LOCATIONBASENOTIFICATION) == 0) {

                                                            msg = task.getString(SharedPreferencesConstant.LEAVE_MESSAGE_LOCAL);
                                                            EliteSession.eLog.d(MODULE, "Local Leave message show");

                                                        } else if (task.getInt(LOCATIONBASENOTIFICATION) == 1) {
                                                            EliteSession.eLog.d(MODULE, "Message Get from the server");
                                                            ConnectionManagerTaskNew connectionTask = new ConnectionManagerTaskNew(null, CoreConstants.MONETIZATION_SENDNOTIFICATIONBYLOCATION_REQUESTID);
                                                            msg = connectionTask.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_SENDNOTIFICATIONBYLOCATION).get();
                                                        } else {
                                                            EliteSession.eLog.d(MODULE, "Message Get from the server else part");
                                                            ConnectionManagerTaskNew connectionTask = new ConnectionManagerTaskNew(null, CoreConstants.MONETIZATION_SENDNOTIFICATIONBYLOCATION_REQUESTID);
                                                            msg = connectionTask.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_SENDNOTIFICATIONBYLOCATION).get();
                                                        }
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    } catch (ExecutionException e) {
                                                        e.printStackTrace();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                    task.saveBoolean(SharedPreferencesConstant.USER_IN_LOCATION, false);
                                                    task.saveLong(SharedPreferencesConstant.USER_IN_LOCATION_ID, 0);

                                                    if (msg.compareTo("") != 0)
                                                        callNFThroghtGCMOrServer(msg);
                                                }
                                            };

                                            Thread tLoadLocation = new Thread(locationRunnable);
                                            tLoadLocation.start();
                                        }
                                    } else {
                                        EliteSession.eLog.d(MODULE, "User not leave the location. ");
                                    }
                                } else {
                                    EliteSession.eLog.d(MODULE, "User not enter any other Location");
                                }
                            }
                        } catch (Exception e) {
                            EliteSession.eLog.d(MODULE, "Locatin parsing error from shared preferences." + e.getMessage());
                        }
                    }
                } else {
                    EliteSession.eLog.d(MODULE, "Location list empty from database.");
                }
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, "Geting location error" + e.getMessage());
            }

        } else {
            EliteSession.eLog.d(MODULE, "IMEI or IMSI not found from application");
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
    }

    private void callAutoConnectService() {

        if (task.getString(CoreConstants.AUTO_CONNECT).equals(CoreConstants.ENABLE)) {

            EliteSession.eLog.d(MODULE, "Auto Connect Enable");
            ComponentName comp = new ComponentName(context.getPackageName(), AutoConnectWiFiService.class.getName());
            ComponentName service = context.startService(new Intent().setComponent(comp));

            if (null == service) {
                // something really wrong here
                EliteSession.eLog.d(MODULE, "Could not start service " + comp.toString());
            }
        } else {
            EliteSession.eLog.d(MODULE, "Auto Connect Disable");
        }
    }

    public class LocalBinder extends Binder {
        public BackgroundLocationService getServerInstance() {
            return BackgroundLocationService.this;
        }
    }
}