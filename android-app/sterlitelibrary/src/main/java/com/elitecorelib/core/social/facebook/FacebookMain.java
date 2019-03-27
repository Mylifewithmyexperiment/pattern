package com.elitecorelib.core.social.facebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

/**
 * Created by Chirag Parmar on 05-Jul-16.
 */
public class FacebookMain {

    private static FacebookMain mainClass;
    private static final String MODULE = "FacebookMain";
    private Context mContext;
    private LoginButton mLoginbutton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private static  FacebookInterface mListner;

    public FacebookMain(Context con) {
        this.mContext = mContext;
        callbackManager = CallbackManager.Factory.create();
    }

    public static FacebookMain newInstance(Context context) {

        if (mainClass == null) {
            mainClass = new FacebookMain(context);
            FacebookSdk.sdkInitialize(context);
            EliteSession.eLog.d(MODULE, "Inisilize of facebook SDK");
        }
        return mainClass;
    }

    public void facebookLogin(LoginButton loginButton, String permission[],FacebookInterface listner) {
        this.mLoginbutton = loginButton;
        mListner = listner;
        EliteSession.eLog.d(MODULE, "ReadPermission of Facebook : " + permission);
        EliteSession.eLog.d(MODULE, "Click Facebook Button");

        mLoginbutton.setReadPermissions(permission);
        loginButton.performClick();
        loginButton.setPressed(true);
        loginButton.invalidate();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                EliteSession.eLog.d(MODULE, "SuccessFully Registration from facebook");
                accessToken = loginResult.getAccessToken();
                getUserProfile(accessToken);
                EliteSession.eLog.d(MODULE, "AccessToken - " + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                EliteSession.eLog.d(MODULE, "Facebook registation cancel");
                mListner.onFacebookCancel();
            }

            @Override
            public void onError(FacebookException error) {
                EliteSession.eLog.d(MODULE, "Facebook registation error : " + error.getMessage());
                mListner.onFacebookCancel();
            }
        });

        loginButton.setPressed(false);
        loginButton.invalidate();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        EliteSession.eLog.d(MODULE, "User Login with Facebook");
    }

    public void facebookLogout() {
        LoginManager.getInstance().logOut();
        EliteSession.eLog.d(MODULE, "Logout from facebook");
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    /**
     * getFacebook Profile method
     * @param accessToken
     */
    public void getUserProfile(AccessToken accessToken) {
        EliteSession.eLog.d(MODULE, "Get Facebook User Profile");
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override

                    public void onCompleted(JSONObject object, GraphResponse response) {
                        EliteSession.eLog.d(MODULE, response.toString());
                        if (object != null) {
                            LibraryApplication.getLibraryApplication().setFaceBookUser(object);
                            mListner.onFacebookSuccess();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
