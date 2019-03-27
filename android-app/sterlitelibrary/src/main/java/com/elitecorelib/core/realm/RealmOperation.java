package com.elitecorelib.core.realm;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.graphics.PointF;

import com.elitecorelib.andsf.pojo.ANDSFDiscoveryInformations;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;
import com.elitecorelib.andsf.pojo.ANDSFPrioritizedAccess;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.pojo.PojoBlackListData;
import com.elitecorelib.core.pojo.PojoLocation;
import com.elitecorelib.core.pojo.PojoSubscriber;
import com.elitecorelib.core.pojo.PojoSyncData;
import com.elitecorelib.core.pojo.PojoWiFiConnection;
import com.elitecorelib.core.pojo.PojoWiFiProfile;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.elitecorelib.deal.pojo.PojoDealAll;
import com.elitecorelib.deal.pojo.PojoDealDisplayInfo;
import com.elitecorelib.deal.pojo.PojoDealTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static com.elitecorelib.core.CoreConstants.NEARBYDISTANCE;
import static com.elitecorelib.core.CoreConstants.NEARBYDISTANCE_VALUE;
import static com.elitecorelib.core.realm.RealmConstant.ISPREFERABLE;
import static com.elitecorelib.core.realm.RealmConstant.PROFILEID;

/**
 * Created by Chirag Parmar on 12/03/18.
 */

public class RealmOperation {

    private static RealmOperation instance;
    private final String MODULE = "RealmOperation";
    private final Realm realm;
    private final SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    public RealmOperation(Context application) {
        this.realm = Realm.getDefaultInstance();
    }

    public static RealmOperation with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmOperation(fragment.getActivity().getApplicationContext());
        }
        return instance;
    }

    public static RealmOperation with(Activity activity) {

        if (instance == null) {
            instance = new RealmOperation(activity.getApplicationContext());
        }
        return instance;
    }

    public static RealmOperation with(Application application) {

        if (instance == null) {
            instance = new RealmOperation(application.getApplicationContext());
        }
        return instance;
    }

    public static RealmOperation with(Context application) {

        instance = new RealmOperation(application);
        return instance;
    }

    public static RealmOperation getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }


    public void insertData(final Object pojo) {

        try {
            realm.beginTransaction();

            if (pojo instanceof PojoLocation) {
                PojoLocation resPojo = (PojoLocation) pojo;
                realm.insertOrUpdate(resPojo);

            } else if (pojo instanceof PojoSubscriber) {
                PojoSubscriber resPojo = (PojoSubscriber) pojo;
                realm.insertOrUpdate(resPojo);
            } else if (pojo instanceof PojoBlackListData) {
                PojoBlackListData resPojo = (PojoBlackListData) pojo;
                realm.insertOrUpdate(resPojo);

            } else if (pojo instanceof PojoWiFiProfile) {

                PojoWiFiProfile profilePoJo = (PojoWiFiProfile) pojo;
                Number currentIdNum = realm.where(PojoWiFiProfile.class).max(PROFILEID);
                int nextId;
                if (currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }
                EliteSession.eLog.d(MODULE, "Profile ID - " + nextId);
                profilePoJo.setProfileId(nextId);

                if (profilePoJo.getWifiSettingSet() != null && profilePoJo.getWifiSettingSet().size() > 0) {

                    Number currentIDConn = realm.where(PojoWiFiConnection.class).max(RealmConstant.ID);

                    for (PojoWiFiConnection pojoConnection : profilePoJo.getWifiSettingSet()) {

                        if (currentIDConn == null) {
                            currentIDConn = 1;
                        } else {
                            currentIDConn = currentIDConn.intValue() + 1;
                        }

                        EliteSession.eLog.d(MODULE, "currentID ID - " + currentIDConn);

                        pojoConnection.setId(currentIDConn.intValue());
                        pojoConnection.setProfileId(nextId);
                        pojoConnection.setLocal(false);
                    }
                }

                realm.insertOrUpdate(profilePoJo);
            } else if (pojo instanceof PojoWiFiConnection) {

                PojoWiFiConnection wiFiConnection = (PojoWiFiConnection) pojo;

                Number currentIDConn = realm.where(PojoWiFiConnection.class).max(RealmConstant.ID);

                if (currentIDConn == null) {
                    currentIDConn = 1;
                } else {
                    currentIDConn = currentIDConn.intValue() + 1;
                }

                EliteSession.eLog.d(MODULE, "currentID ID - " + currentIDConn);

                wiFiConnection.setId(currentIDConn.intValue());
                wiFiConnection.setLocal(true);
                realm.insertOrUpdate(wiFiConnection);


            } else if (pojo instanceof PojoDealAll) {
                PojoDealAll resPojo = (PojoDealAll) pojo;

                Number currentIdNum = realm.where(PojoDealAll.class).max(RealmConstant.DEALID);
                int nextId;
                if (currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }
                EliteSession.eLog.d(MODULE, "Profile ID - " + nextId);
                //resPojo.setDealId((long) nextId);

                if (resPojo.getDealDisplayInfoData() != null) {

                    Number currentIDConn = realm.where(PojoDealDisplayInfo.class).max(RealmConstant.ID);

                    if (currentIDConn == null) {
                        currentIDConn = 1;
                    } else {
                        currentIDConn = currentIDConn.intValue() + 1;
                    }

                    resPojo.getDealDisplayInfoData().setId(currentIDConn.intValue());
                    resPojo.getDealDisplayInfoData().setDealId((long) nextId);
                }
                realm.insertOrUpdate(resPojo);
            } else if (pojo instanceof PojoDealDisplayInfo) {
                PojoDealDisplayInfo resPojo = (PojoDealDisplayInfo) pojo;
                realm.insertOrUpdate(resPojo);
            } else if (pojo instanceof PojoDealTag) {
                PojoDealTag resPojo = (PojoDealTag) pojo;
                realm.insertOrUpdate(resPojo);
            } else if (pojo instanceof PojoSyncData) {
                PojoSyncData resPojo = (PojoSyncData) pojo;
                realm.insertOrUpdate(resPojo);
            } else if(pojo instanceof ANDSFDiscoveryInformations) {
                ANDSFDiscoveryInformations resPojo = (ANDSFDiscoveryInformations) pojo;
                realm.insertOrUpdate(resPojo);
            } else if(pojo instanceof ANDSFPolicies) {
                ANDSFPolicies resPojo = (ANDSFPolicies) pojo;
                realm.insertOrUpdate(resPojo);
            }
            realm.commitTransaction();

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error Insert Data - " + e.getMessage());
        }
    }

    public void deleteAll(Class className) {
        try {
            realm.beginTransaction();
            EliteSession.eLog.d(MODULE, "Delete Table name = " + className);
            realm.delete(className);
            realm.commitTransaction();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error Delete Data - " + e.getMessage());
        }
    }

    public ArrayList<PojoLocation> getAllHotspot() {

        try {
            ArrayList<PojoLocation> hotspotList = new ArrayList<>();
            EliteSession.eLog.d(MODULE, "Invoke Get All Hotspot");
            RealmResults<PojoLocation> list = realm.where(PojoLocation.class).findAll();
            hotspotList.addAll(list);
            EliteSession.eLog.d(MODULE, "List Size - " + list.size());

            return hotspotList;

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in getAllHotspot - " + e.getMessage());
        }
        return null;
    }

    public ArrayList<ANDSFDiscoveryInformations> getALLDiscoveryInformation() {

        try {
            ArrayList<ANDSFDiscoveryInformations> discoveryInformationsList = new ArrayList<>();
            EliteSession.eLog.d(MODULE, "Invoke Get All ANDSFDiscoveryInformations");
            RealmResults<ANDSFDiscoveryInformations> list = realm.where(ANDSFDiscoveryInformations.class).findAll();
            discoveryInformationsList.addAll(list);
            EliteSession.eLog.i(MODULE,"ANDSF DiscoveryInformation Total - "+list.size());

            return discoveryInformationsList;

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in getAllHotspot - " + e.getMessage());
        }
        return null;
    }

    public ArrayList<ANDSFPolicies> getAllPolicies() {

        try {
            ArrayList<ANDSFPolicies> policiesArrayList = new ArrayList<>();
            EliteSession.eLog.d(MODULE, "Invoke Get All policiesArrayList");
            RealmResults<ANDSFPolicies> list = realm.where(ANDSFPolicies.class).findAll();
            policiesArrayList.addAll(list);
            EliteSession.eLog.i(MODULE,"ANDSF Polices Total - "+list.size());
            return policiesArrayList;
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in policiesArrayList - " + e.getMessage());
        }
        return null;
    }

    public ArrayList<PojoLocation> getNearByHotspot() {
        ArrayList<PojoLocation> hotspotList = new ArrayList<>();
        try {

            if (spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE) != null && !spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE).equals("")) {
                PointF cPoint = new PointF(Float.parseFloat(spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE)),
                        Float.parseFloat(spTask.getString(SharedPreferencesConstant.CURRENT_LONGITUDE)));
                double DISTANCE = Double.valueOf(ElitelibUtility.getKeyPairvalue(NEARBYDISTANCE, NEARBYDISTANCE_VALUE));
                PointF pointA = ElitelibUtility.calculateDerivedPosition(cPoint, DISTANCE, 0);
                PointF pointB = ElitelibUtility.calculateDerivedPosition(cPoint, DISTANCE, 90);
                PointF pointC = ElitelibUtility.calculateDerivedPosition(cPoint, DISTANCE, 180);
                PointF pointD = ElitelibUtility.calculateDerivedPosition(cPoint, DISTANCE, 270);


                EliteSession.eLog.d(MODULE, "Invoke Get NearBy Hotspot");

                RealmResults<PojoLocation> list = realm.where(PojoLocation.class).
                        greaterThan(RealmConstant.LATITUDE, Double.valueOf(pointC.x))
                        .and()
                        .lessThan(RealmConstant.LATITUDE, Double.valueOf(pointA.x))
                        .and()
                        .lessThan(RealmConstant.LONGITUDE, Double.valueOf(pointB.y))
                        .and()
                        .greaterThan(RealmConstant.LONGITUDE, Double.valueOf(pointD.y))
                        .findAll();
                hotspotList.addAll(list);

            } else {

                EliteSession.eLog.d(MODULE, "Current Location not available, getting all Hotspot ");
                RealmResults<PojoLocation> list = realm.where(PojoLocation.class).findAll();
                hotspotList.addAll(list);
                EliteSession.eLog.d(MODULE, "NearByList Size - " + hotspotList.size());
            }

            return hotspotList;

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in getNearByHotspot - " + e.getMessage());
        }
        return hotspotList;
    }

    public ArrayList<PojoWiFiProfile> getWiFiProfile() {

        ArrayList<PojoWiFiProfile> pojoWiFiProfiles = new ArrayList<>();

        try {
            EliteSession.eLog.d(MODULE, "Invoke Get WiFiProfile");
            RealmResults<PojoWiFiProfile> list = realm.where(PojoWiFiProfile.class).findAll();
            pojoWiFiProfiles.addAll(list);

            EliteSession.eLog.d(MODULE, "Wifi Profile count - " + pojoWiFiProfiles.size());

            return pojoWiFiProfiles;

        } catch (Exception ex) {
            EliteSession.eLog.e(MODULE, "Error in GetWiFiProfile - " + ex.getMessage());
        }
        return pojoWiFiProfiles;
    }

    public ArrayList<PojoWiFiConnection> getWiFiConnection() {

        ArrayList<PojoWiFiConnection> PojoWiFiProfiles = new ArrayList<>();

        try {
            EliteSession.eLog.d(MODULE, "Invoke Get WiFiProfile");
            RealmResults<PojoWiFiConnection> list = realm.where(PojoWiFiConnection.class).findAll();
            EliteSession.eLog.d(MODULE, "Connection List Size - " + list.size());
            PojoWiFiProfiles.addAll(list);
            return PojoWiFiProfiles;

        } catch (Exception ex) {
            EliteSession.eLog.e(MODULE, "Error in getWiFiConnection - " + ex.getMessage());
        }
        return PojoWiFiProfiles;
    }

    public ArrayList<PojoDealAll> getDeals(String TagID) {

        ArrayList<PojoDealAll> pojoDealAllResponses = new ArrayList<>();

        try {
            EliteSession.eLog.d(MODULE, "Invoke Get Deals");
            RealmResults<PojoDealAll> list;
            if (TagID.equals("") || TagID.equals(null) || TagID == null) {
                list = realm.where(PojoDealAll.class).findAll();
            } else {
                list = realm.where(PojoDealAll.class)
                        .equalTo(RealmConstant.DEALTAGS, TagID)
                        .findAll();
            }

            //EliteSession.eLog.e(MODULE, "Value : "+list.get(0).getDealDisplayInfoData().getDealHeadline());
            pojoDealAllResponses.addAll(list);

            EliteSession.eLog.e(MODULE, "Pojo " + pojoDealAllResponses.get(0).getDealName());
            EliteSession.eLog.e(MODULE, "Pojo " + pojoDealAllResponses.get(0).getDealDisplayInfoData().getDisplayDealName());


            EliteSession.eLog.d(MODULE, "Deals count - " + pojoDealAllResponses.size());

            return pojoDealAllResponses;

        } catch (Exception ex) {
            EliteSession.eLog.e(MODULE, "Error in GetDeals - " + ex.getMessage());
        }
        return pojoDealAllResponses;
    }

    public ArrayList<PojoDealTag> getDealsCategory() {

        ArrayList<PojoDealTag> pojoDealTag = new ArrayList<>();

        try {
            EliteSession.eLog.d(MODULE, "Invoke Get Deals");
            RealmResults<PojoDealTag> list = realm.where(PojoDealTag.class).findAll();
            //EliteSession.eLog.e(MODULE, "Value : "+list.get(0).getDealDisplayInfoData().getDealHeadline());
            pojoDealTag.addAll(list);

            //EliteSession.eLog.e(MODULE, "Pojo "+pojoDealTag.get(0).getDealName());
            //EliteSession.eLog.e(MODULE, "Pojo "+pojoDealTag.get(0).getDealDisplayInfoData().getDisplayDealName());


            EliteSession.eLog.d(MODULE, "Deals Category count - " + pojoDealTag.size());

            return pojoDealTag;

        } catch (Exception ex) {
            EliteSession.eLog.e(MODULE, "Error in getDealsCategory - " + ex.getMessage());
        }
        return pojoDealTag;
    }


    public ArrayList<PojoSyncData> getSyncData() {

        final ArrayList<PojoSyncData> pojoSyncData = new ArrayList<>();

        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    EliteSession.eLog.d(MODULE, "Invoke Get SyncData");
                    RealmResults<PojoSyncData> list = realm.where(PojoSyncData.class).findAll();
                    //EliteSession.eLog.e(MODULE, "Value : "+list.get(0).getDealDisplayInfoData().getDealHeadline());
                    pojoSyncData.addAll(list);
                }
            });

            //EliteSession.eLog.e(MODULE, "Pojo "+pojoSyncData.get(0).getDealName());
            //EliteSession.eLog.e(MODULE, "Pojo "+pojoSyncData.get(0).getDealDisplayInfoData().getDisplayDealName());


            EliteSession.eLog.d(MODULE, "Sync Data count - " + pojoSyncData.size());

            return pojoSyncData;

        } catch (Exception ex) {
            EliteSession.eLog.e(MODULE, "Error in getSyncData - " + ex.getMessage());
        }
        return pojoSyncData;
    }

    public void updateData(final Object pojo, final String... args) {

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    if (pojo instanceof PojoWiFiProfile) {
                        PojoWiFiProfile profilePoJo = (PojoWiFiProfile) pojo;
                        EliteSession.eLog.d(MODULE, profilePoJo.getAndroidSettingName() + "-" + Boolean.valueOf(args[0]));
                        profilePoJo.setPreferable(Boolean.valueOf(args[0]));
                        realm.copyToRealmOrUpdate(profilePoJo);

                    } else if (pojo instanceof PojoWiFiConnection) {

                        PojoWiFiConnection wiFiConnection = (PojoWiFiConnection) pojo;

                        wiFiConnection.setWisprUsarname(args[0]);
                        wiFiConnection.setWisprPassword(args[1]);
                        realm.copyToRealmOrUpdate(wiFiConnection);

                    }else if (pojo instanceof PojoSyncData) {

                        PojoSyncData syncData = (PojoSyncData) pojo;

                        syncData.setModifiedDate(args[0]);
                        realm.copyToRealmOrUpdate(syncData);

                    }
                }
            });

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error Update Data - " + e.getMessage());
        }
    }

    public void updatePojoData(final Object pojo) {

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    if (pojo instanceof PojoWiFiConnection) {
                        PojoWiFiConnection wiFiConnection = (PojoWiFiConnection) pojo;
                        realm.insertOrUpdate(wiFiConnection);
                    }
                }
            });

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error Update Data - " + e.getMessage());
        }
    }

    public void deleteItem(RealmObject object) {

        try {
            realm.beginTransaction();
            object.deleteFromRealm();
            realm.commitTransaction();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error Delete  Data - " + e.getMessage());
        }
    }


    public void sortDescoveryInformation(List<ANDSFPrioritizedAccess> objectArrayList) {
        try {
            realm.beginTransaction();
            Collections.sort(objectArrayList);
            realm.commitTransaction();
        }catch (Exception e) {
            EliteSession.eLog.e(MODULE, "ErrorDescoveryInformation - " + e.getMessage());
        }
    }





    public PojoWiFiProfile updateWiFiProfileName(final PojoWiFiProfile wiFiProfile, final String profileName) {
        try {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    PojoWiFiProfile profile = realm.where(PojoWiFiProfile.class)
                            .equalTo(PROFILEID, wiFiProfile.getProfileId())
                            .findFirst();
                    profile.setAndroidSettingName(profileName);
                }
            });

            EliteSession.eLog.d(MODULE, "Update value  - " + wiFiProfile.getAndroidSettingName());

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "updateWiFiProfileName - " + e.getMessage());
        }
        return wiFiProfile;
    }

    public void updateWiFiConfiguration(final PojoWiFiProfile wiFiProfile, final ArrayList<PojoWiFiConnection> wiFiConnections) {

        try {

            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    PojoWiFiProfile connections = realm.where(PojoWiFiProfile.class)
                            .equalTo(PROFILEID, wiFiProfile.getProfileId())
                            .findFirst();

                    boolean isRemove = connections.getWifiSettingSet().removeAll(connections.getWifiSettingSet());
                    EliteSession.eLog.d(MODULE, "remove All From - " + isRemove);

                    if (wiFiConnections.size() > 0) {
                        RealmList<PojoWiFiConnection> connectionRealmList = new RealmList<>();
                        for (PojoWiFiConnection connection : wiFiConnections) {
                            connection.setProfileId(wiFiProfile.getProfileId());
                            connectionRealmList.add(connection);
                        }
                        wiFiProfile.setWifiSettingSet(connectionRealmList);
                        realm.copyToRealmOrUpdate(wiFiProfile);
                    }
                }
            });

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Update WiFi Connection - " + e.getMessage());
        }
    }

    public void removeWiFiConnection(final PojoWiFiProfile wiFiProfile, final int position) {

        try {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    PojoWiFiProfile profile = realm.where(PojoWiFiProfile.class)
                            .equalTo(PROFILEID, wiFiProfile.getProfileId())
                            .findFirst();

                    profile.getWifiSettingSet().remove(position);
                    realm.copyToRealmOrUpdate(profile);
                }
            });


        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Remove WiFi Coneciton - " + e.getMessage());
        }
    }


    public PojoWiFiProfile getActiveWiFi() {

        try {

            PojoWiFiProfile activeProfile = realm.where(PojoWiFiProfile.class)
                    .equalTo(ISPREFERABLE, true)
                    .findFirst();

            return activeProfile;

        } catch (Exception ex) {
            EliteSession.eLog.e(MODULE, "getActiveWiFi Coneciton - " + ex.getMessage());
        }

        return null;
    }
}
