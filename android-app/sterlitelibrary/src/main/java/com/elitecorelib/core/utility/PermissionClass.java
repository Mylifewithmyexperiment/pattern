package com.elitecorelib.core.utility;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

import com.elitecore.eliteconnectlibrary.R;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.interfaces.OnPermissionListner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chirag Parmar on 14-Jul-16.
 */
public class PermissionClass {

    private static PermissionClass permissionClass;
    private final String MODULE = "PermissionClass";
    private final int ALL_REQUEST_CONSTANT = 1, REQUEST_CONSTANT = 101;
    private final Activity mActivity;
    private final OnPermissionListner mListner;
    private boolean isGranted = true;
    private String[] permissions;
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    public PermissionClass(Activity activity, OnPermissionListner listner) {
        this.mActivity = activity;
        this.mListner = listner;

        getPermissionList();
    }

    public static PermissionClass getInstance(Activity activity, OnPermissionListner listner) {

        if (permissionClass == null) {
            permissionClass = new PermissionClass(activity, listner);
        }
        return permissionClass;
    }

    public boolean isAboveMarshMellow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public void isPermissionGrant(final String permission, String permissionDetail, String permissionRationaleDetail) {

        if (ContextCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_GRANTED) {
            mListner.isPermissionGranted(true, permission);
            spTask.saveBoolean(permission,true);
            spTask.saveBoolean("isFirst_"+permission,true);
        } else {

            boolean isRationale = ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
            EliteSession.eLog.d(MODULE, " ShowRequestPermissionRationale - " + isRationale);
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mActivity, R.style.AlertDialog));
            builder.setTitle(mActivity.getString(R.string.permission));
            builder.setCancelable(false);

            if (isRationale || spTask.getBoolean(permission)) {

                builder.setMessage(permissionDetail);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EliteSession.eLog.i(MODULE, "Permission OK Button CLick");
                        spTask.saveBoolean(permission, false); // Check the first time permission for the Show Dialog
                        if (isAboveMarshMellow())
                            mActivity.requestPermissions(new String[]{permission}, REQUEST_CONSTANT);
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EliteSession.eLog.i(MODULE, "Cancel Button Click");
                        dialogInterface.cancel();
                        mListner.isPermissionGranted(false, permission);
                    }
                });

                AlertDialog dialog = builder.create();
                if (!dialog.isShowing())
                    dialog.show();

            } else {

                if (spTask.getBoolean("isFirst_"+permission)) {
                    EliteSession.eLog.i(MODULE, "First time show the setting Dialog with this permission");
                    spTask.saveBoolean("isFirst_"+permission, false);
                    builder.setMessage(permissionRationaleDetail);
                    builder.setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EliteSession.eLog.i(MODULE, "Permission Setting Button CLick");

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", mActivity.getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mActivity.startActivity(intent);
                        }
                    });

                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EliteSession.eLog.i(MODULE, "Cancel Button Click");
                            dialogInterface.cancel();
                            mListner.isPermissionGranted(false, permission);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    if (!dialog.isShowing())
                        dialog.show();
                } else {
                    mListner.isPermissionGranted(false, permission);
                    EliteSession.eLog.i(MODULE, "Second time show the setting Dialog with this permission");
                }
            }
        }
    }

    /**
     * Using this method you can get the all @permission @List from you add in AndroidMenifest file.
     */
    private void getPermissionList() {
        try {

            PackageInfo packageInfo1 = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            final List<PackageInfo> installedApps = mActivity.getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA);
            for (PackageInfo app : installedApps) {

                if (app.packageName.equalsIgnoreCase(mActivity.getPackageName())) {
                    PackageInfo packageInfo = mActivity.getPackageManager().getPackageInfo(app.packageName, PackageManager.GET_PERMISSIONS);
                    if (packageInfo.requestedPermissions != null) {
                        permissions = packageInfo.requestedPermissions;
                    }
                    break;
                }
            }
//            EliteSession.eLog.d(MODULE, Arrays.toString(permissions));
        } catch (PackageManager.NameNotFoundException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    /**
     * Using this method you declare the permission
     */
    public void checkPermissionList() {

        ArrayList<String> unGrantedPermissionList = new ArrayList<>();

        for (String permission : permissions) {

            try {
                int permissionStatus = ContextCompat.checkSelfPermission(mActivity, permission);
                EliteSession.eLog.d(MODULE, permission + " = " + permissionStatus);

                int permissionLevel = mActivity.getPackageManager().getPermissionInfo(permission, PackageManager.GET_PERMISSIONS).protectionLevel;

                if (permissionStatus != PackageManager.PERMISSION_GRANTED && permissionLevel == PermissionInfo.PROTECTION_DANGEROUS) {
                    unGrantedPermissionList.add(permission);
                }

            } catch (PackageManager.NameNotFoundException nn) {
                EliteSession.eLog.d(MODULE, permission + " Permission Name not found in List");
            }
        }

        EliteSession.eLog.d(MODULE, "Not Granted Permission" + " = " + unGrantedPermissionList);

        if (unGrantedPermissionList != null && unGrantedPermissionList.size() > 0) {
            String[] list = new String[unGrantedPermissionList.size()];
            list = unGrantedPermissionList.toArray(list);
            if (isAboveMarshMellow())
                mActivity.requestPermissions(list, ALL_REQUEST_CONSTANT);

        } else {
            EliteSession.eLog.d(MODULE, "All Permission Grandted = " + isGranted);
            mListner.isPermissionGranted(isGranted, permissions.toString());
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        EliteSession.eLog.d(MODULE, "Call onRequestPermissionsResult");
        EliteSession.eLog.d(MODULE, Arrays.toString(permissions));

        switch (requestCode) {

            case ALL_REQUEST_CONSTANT:

                for (int i = 0; i < permissions.length; i++) {
                    EliteSession.eLog.d(MODULE, permissions[i] + " == " + grantResults[i]);

                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        isGranted = false;
                        break;
                    }
                    isGranted = true;
                }
                EliteSession.eLog.d(MODULE, "All Permission Grandted = " + isGranted);
                mListner.isPermissionGranted(isGranted, permissions.toString());

                break;

            case REQUEST_CONSTANT:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mListner.isPermissionGranted(true, permissions[0]);
                    EliteSession.eLog.i(MODULE,permissions[0]+" - Granted");
                } else {
                    EliteSession.eLog.i(MODULE,permissions[0]+" - Not Granted");
                    mListner.isPermissionGranted(false, permissions[0]);
                }

                break;
        }
    }
}
