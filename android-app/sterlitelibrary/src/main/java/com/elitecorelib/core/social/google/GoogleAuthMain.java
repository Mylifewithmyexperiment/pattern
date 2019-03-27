package com.elitecorelib.core.social.google;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

/**
 * Created by Chirag Parmar on 11-Jul-16.
 */
public class GoogleAuthMain implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private final String MODULE = "GoogleAuthMain";
    private final int RC_SIGN_IN = 100;
    private FragmentActivity mActivity;
    private GoogleApiClient mGoogleApiClient;
    private GoogleInterface mListner;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;
    private boolean isGoogleClicked;
    private Person person = null;
    private boolean mResolvingError = false;

    public GoogleAuthMain(FragmentActivity activity, GoogleInterface listner) {

        this.mActivity = activity;
        this.mListner = listner;

        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        signOutGoogle();
    }

    // [START signOut]
    public void signOutGoogle() {
        EliteSession.eLog.d(MODULE, "onclick Google Logout Clicked");
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();

        }
    }
    // [END signOut]

    public void onStart() {
//        mGoogleApiClient.connect();
    }

    public void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void signInGoogle() {
        isGoogleClicked = true;
        mGoogleApiClient.connect();
    }

    /**
     * Method to resolve any signin errors
     */
    private void resolveSignInError() {
        if (mConnectionResult != null && mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(mActivity, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            // Make sure the app is not already connected or attempting to connect
            if (!mGoogleApiClient.isConnecting() &&
                    !mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        } else if(resultCode == Activity.RESULT_CANCELED) {
            mGoogleApiClient.disconnect();
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (!isGoogleClicked)
            return;

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            EliteSession.eLog.d(MODULE, "onConnected Google Login Connected");
            LibraryApplication.getLibraryApplication().setGoogleUser(person);

            if (mListner != null)
                mListner.onGoogleAuthSuccess();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        onDisconnected();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {

        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(mActivity, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GooglePlayServicesUtil.getErrorDialog()
//            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
        mListner.onGoogleAuthFailor();
    }

    public void onDisconnected() {
        mGoogleApiClient.connect();
        mListner.onGoogleAuthCancel();
        EliteSession.eLog.d(MODULE, "onDisconnected Google,Reset login variables");
    }

//    /* Creates a dialog for an error message */
//    private void showErrorDialog(int errorCode) {
//        // Create a fragment for the error dialog
//        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
//        // Pass the error that should be displayed
//        Bundle args = new Bundle();
//        args.putInt(DIALOG_ERROR, errorCode);
//        dialogFragment.setArguments(args);
//        dialogFragment.show(getSupportFragmentManager(), "errordialog");
//    }
//
//    /* A fragment to display an error dialog */
//    public class ErrorDialogFragment extends DialogFragment {
//        private final FragmentActivity mActivity;
//
//        public ErrorDialogFragment(FragmentActivity activity) {
//            this.mActivity = activity;
//        }
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Get the error code and retrieve the appropriate dialog
//            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
//            return GooglePlayServicesUtil.getErrorDialog(errorCode,
//                    this.getActivity(), REQUEST_RESOLVE_ERROR);
//        }
//
//        @Override
//        public void onDismiss(DialogInterface dialog) {
//            ((RegistrationActivity) getActivity()).onDialogDismissed();
//        }
//    }

}
