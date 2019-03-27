package com.elitecorelib.analytics.realm;

import com.elitecorelib.analytics.pojo.AnalyticsOffloadSuccess;
import com.elitecorelib.analytics.realm.AnalyticsRealmClassInvoke;
import com.elitecorelib.core.EliteSession;

import java.util.ArrayList;
import java.util.List;


import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;


public class AnalyticsRealmManager<T extends RealmModel> {
    protected final Realm realm = AnalyticsRealmClassInvoke.getAnalyticsRealm();
    protected final Class<T> type;
    private final String MODULE;

    public AnalyticsRealmManager(Class<T> type) {
        this.type = type;
        MODULE = "AnalyticsRealmManager[" + type.getSimpleName() + "]";
    }

    public Realm getRealm() {
        return realm;
    }

    public void insertData(final T pojo) {
        try {
            realm.beginTransaction();
            realm.insertOrUpdate(pojo);
            realm.commitTransaction();
            realm.refresh();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error Insert Data - " + e.getMessage());

        }
    }

    public void insertAll(final List<T> pojoList) {
        EliteSession.eLog.d(MODULE, "Inside insertAll pojoList " + pojoList);
        try {
            if (pojoList != null && !pojoList.isEmpty()) {
                realm.beginTransaction();
                realm.insertOrUpdate(pojoList);
                realm.commitTransaction();
                realm.refresh();
                EliteSession.eLog.d(MODULE, "Inside insertAll pojoList " + pojoList);
            }
            realm.refresh();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error Insert Data - " + e.getMessage());
        }
    }

    public List<T> getAll() {
        try {
            ArrayList<T> classList = new ArrayList<>();
            EliteSession.eLog.d(MODULE, "Invoke Get All");
            RealmResults<T> list = realm.where(type).findAll();
            classList.addAll(list);
            return classList;

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in getAll - " + e.getMessage());
        }
        return null;

    }

    public List<T> getByQuery(RealmQuery<T> query) {
        try {
            ArrayList<T> classList = new ArrayList<>();
            EliteSession.eLog.d(MODULE, "Invoke Get All");
            RealmResults<T> list = query.findAll();
            classList.addAll(list);
            EliteSession.eLog.d(MODULE, "List Size - " + list.size());
            return classList;

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in getByQuery - " + e.getMessage());
        }
        return null;

    }

    public T getFirstByQuery(RealmQuery<T> query) {
        try {

            EliteSession.eLog.d(MODULE, "Invoke getFirstByQuery");
            T pojoObject = query.findFirst();
            return pojoObject;

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in getFirstByQuery - " + e.getMessage());
        }
        return null;

    }

    public void deleteAll() {
        try {
            realm.beginTransaction();
            EliteSession.eLog.d(MODULE, "Delete Table name = " + type);
            realm.delete(type);
            realm.commitTransaction();
            //realm.refresh();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error Delete Data - " + e.getMessage());
        }
    }

    public int getCountId(String fieldId) {
        Number currentIdNum = realm.where(type).max(fieldId);
        int nextId;
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        EliteSession.eLog.d(MODULE, fieldId + " - " + nextId);
        return nextId;
    }

    public List<T> getLastRecord(int count, String sortField, Sort sortOder) {
        try {
            RealmQuery query = realm.where(type).sort(sortField, sortOder);
            List<T> data = getByQuery(query);
            List<T> nonRealm = realm.copyFromRealm(data);
            if (nonRealm != null && nonRealm.size() <= count)
                return nonRealm;
            List<T> lastRecordList = nonRealm.subList(0, count);
            return lastRecordList;
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in getLastRecord - " + e.getMessage());
        }
        return null;
    }

    public boolean isRecordExist() {
        try {
            Object object = realm.where(type).findFirst();
            if (object != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in isRecordExist - " + e.getMessage());
        }
        return true;
    }

    public void deleteById(long startid, long endid) {
        try {
            EliteSession.eLog.d(MODULE, "Deleting record startid:" + startid + " endId:" + endid);
            realm.beginTransaction();
            RealmQuery query = realm.where(type);
            if (startid > endid) {
                query = query.greaterThanOrEqualTo("id", endid).and().lessThanOrEqualTo("id", startid);
            } else {
                query = query.greaterThanOrEqualTo("id", startid).and().lessThanOrEqualTo("id", endid);
            }
            RealmResults<T> list = query.findAll();
            if (list != null && !list.isEmpty()) {
                list.deleteAllFromRealm();
                realm.commitTransaction();
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in deleteById - " + e.getMessage());
        }
    }
}
