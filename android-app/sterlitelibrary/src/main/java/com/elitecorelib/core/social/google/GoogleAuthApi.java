package com.elitecorelib.core.social.google;

import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

/**
 * Created by Harshesh soni on 21-Mar-18.
 */
public class GoogleAuthApi implements GoogleApiClient.OnConnectionFailedListener{

    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private final String MODULE = "GoogleAuthApi";
    private final int RC_SIGN_IN = 100;
    private FragmentActivity mActivity;
    private GoogleApiClient mGoogleApiClient;
    private GoogleInterface mListner;
    private boolean mResolvingError = false;

    public GoogleAuthApi(FragmentActivity activity, GoogleInterface listner) {

        this.mActivity = activity;
        this.mListner = listner;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signOutGoogle();
    }

    // [START signOut]
    public void signOutGoogle() {
        EliteSession.eLog.d(MODULE, "onclick Google Logout Clicked");
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();

        }
    }
    // [END signOut]

    public void onStart() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            EliteSession.eLog.d(MODULE, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        EliteSession.eLog.d(MODULE, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            EliteSession.eLog.d(MODULE, "onConnected Google Login Connected");
            LibraryApplication.getLibraryApplication().setmGoogleSignInAccount(acct);

            if (mListner != null)
                mListner.onGoogleAuthSuccess();
        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    public void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
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
            mResolvingError = true;
        }
        mListner.onGoogleAuthFailor();
    }

    public void onDisconnected() {
        mGoogleApiClient.connect();
        mListner.onGoogleAuthCancel();
        EliteSession.eLog.d(MODULE, "onDisconnected Google,Reset login variables");
    }
}
