package com.elitecorelib.core.captiveportal;

/**
 * Created by Chirag Parmar on 10-Jan-17.
 */

/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed urnder the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import com.elitecorelib.core.EliteSession;

public class CaptivePortal implements Parcelable {
    /**
     * @hide
     */
    public static final int APP_RETURN_DISMISSED = 0;
    /**
     * @hide
     */
    public static final int APP_RETURN_UNWANTED = 1;
    /**
     * @hide
     */
    public static final int APP_RETURN_WANTED_AS_IS = 2;
    public static final Creator<CaptivePortal> CREATOR
            = new Creator<CaptivePortal>() {
        @Override
        public CaptivePortal createFromParcel(Parcel in) {
            return new CaptivePortal(in.readStrongBinder());
        }

        @Override
        public CaptivePortal[] newArray(int size) {
            return new CaptivePortal[size];
        }
    };
    private final String MODULE = "CaptivePortal";
    private final IBinder mBinder;

    /**
     * @hide
     */
    public CaptivePortal(IBinder binder) {
        mBinder = binder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeStrongBinder(mBinder);
    }

    /**
     * Indicate to the system that the captive portal has been
     * dismissed.  In response the framework will re-evaluate the network's
     * connectivity and might take further action thereafter.
     */
    public void reportCaptivePortalDismissed() {
        try {
            ICaptivePortal.Stub.asInterface(mBinder).appResponse(APP_RETURN_DISMISSED);
        } catch (RemoteException e) {
            EliteSession.eLog.e(MODULE,"reportCaptivePortalDismissed  = "+e.getMessage());
        }
    }

    /**
     * Indicate to the system that the user does not want to pursue signing in to the
     * captive portal and the system should continue to prefer other networks
     * without captive portals for use as the default active data network.  The
     * system will not retest the network for a captive portal so as to avoid
     * disturbing the user with further sign in to network notifications.
     */
    public void ignoreNetwork() {
        try {
            ICaptivePortal.Stub.asInterface(mBinder).appResponse(APP_RETURN_UNWANTED);
        } catch (RemoteException e) {
            EliteSession.eLog.e(MODULE,"ignoreNetwork  = "+e.getMessage());
        }
    }

    /**
     * Indicate to the system the user wants to use this network as is, even though
     * the captive portal is still in place.  The system will treat the network
     * as if it did not have a captive portal when selecting the network to use
     * as the default active data network. This may result in this network
     * becoming the default active data network, which could disrupt network
     * connectivity for apps because the captive portal is still in place.
     *
     * @hide
     */
    public void useNetwork() {
        try {
            ICaptivePortal.Stub.asInterface(mBinder).appResponse(APP_RETURN_WANTED_AS_IS);
        } catch (RemoteException e) {
            EliteSession.eLog.e(MODULE,"useNetwork  = "+e.getMessage());
        }
    }
}