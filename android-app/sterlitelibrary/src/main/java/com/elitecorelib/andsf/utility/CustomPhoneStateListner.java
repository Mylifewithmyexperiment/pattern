package com.elitecorelib.andsf.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.gsm.GsmCellLocation;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesTask;

/**
 * Created by Chirag Parmar on 20/06/18.
 */
public class CustomPhoneStateListner extends PhoneStateListener {

    private final String MODULE = "CustomPhoneStateListner";
    private Context mContext;
    private String mPLMN;
    private GsmCellLocation cellLocation;
    private SharedPreferencesTask sptask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    public CustomPhoneStateListner(Context ctx) {

        this.mContext = ctx;
    }


//    @Override
//    public void onDataConnectionStateChanged(int state, int networkType) {
//        super.onDataConnectionStateChanged(state, networkType);
//
//        EliteSession.eLog.i(MODULE, "state - " + state + " networkType - " + networkType);
//    }
//
//    @Override
//    public void onDataConnectionStateChanged(int state) {
//        super.onDataConnectionStateChanged(state);
//
//
//        EliteSession.eLog.i(MODULE, "onDataConnectionStateChanged" + state);
//    }
//
//
//    @Override
//    public void onCellInfoChanged(List<CellInfo> cellInfo) {
//        super.onCellInfoChanged(cellInfo);
//        EliteSession.eLog.i(MODULE, "onCellInfoChanged " + cellInfo);
//
//    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);

        if (location instanceof GsmCellLocation) {
            GsmCellLocation gcLoc = (GsmCellLocation) location;
            this.cellLocation = gcLoc;

            sptask.saveString(CoreConstants.LAC_VALUE, String.valueOf(gcLoc.getLac()));
            sptask.saveString(CoreConstants.CELL_ID_VALUE, String.valueOf(gcLoc.getCid()));

            EliteSession.eLog.i(MODULE, "onCellLocationChanged: getCid " + gcLoc.getCid());
            EliteSession.eLog.i(MODULE, "onCellLocationChanged: getLac " + gcLoc.getLac());
        } else {
            EliteSession.eLog.i(MODULE, "onCellLocationChanged: " + location.toString());
        }
    }

//    @Override
//    public void onServiceStateChanged(ServiceState serviceState) {
//        super.onServiceStateChanged(serviceState);
//
//        EliteSession.eLog.i(MODULE, "getIsManualSelection = " + serviceState.getIsManualSelection());
//        EliteSession.eLog.i(MODULE, "getOperatorAlphaLong = " + serviceState.getOperatorAlphaLong());
//        EliteSession.eLog.i(MODULE, "getRoaming = " + serviceState.getRoaming());
//        EliteSession.eLog.i(MODULE, "getRoaming = " + serviceState.toString());
//
//        mPLMN = serviceState.getOperatorNumeric();
//        EliteSession.eLog.i(MODULE, "PLMN is = " + mPLMN);
//    }
//
//    public String getmPLMN() {
//        return mPLMN;
//    }

    public GsmCellLocation getCellLocation() {
        return cellLocation;
    }
}
