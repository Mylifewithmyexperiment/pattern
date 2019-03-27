package com.elitecorelib.wifi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elitecorelib.andsf.api.ANDSFClient;
import com.elitecorelib.andsf.pojo.ANDSFConfig;
import com.elitecorelib.core.LibraryApplication;

public class BootReceiver extends BroadcastReceiver {
    private final String MODULE = "BootReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            ANDSFClient client = ANDSFClient.getClient();
            ANDSFConfig config = new ANDSFConfig();
            config.setPolicyCall(false);
            config.setContext(LibraryApplication.getLibraryApplication().getLibraryContext());
            client.invokeANDSFClient(config);

        } catch (Exception e) {
            Log.e(MODULE, "Error in  - " + e.getMessage());
        }

    }


}
