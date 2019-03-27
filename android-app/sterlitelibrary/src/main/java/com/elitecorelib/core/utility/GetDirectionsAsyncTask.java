package com.elitecorelib.core.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import com.elitecore.eliteconnectlibrary.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by Chirag Parmar on 3/16/2017.
 */


public class GetDirectionsAsyncTask extends AsyncTask<Map<String, LatLng>, Object, ArrayList> {

    public static final String CURRENT_LATLNG = "CURRENT_LATLNG";
    public static final String DESTINATION_LATLNG = "DESTINATION_LATLNG";

    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";

    private String DIRECTIONS_MODE = "directions_mode";
    private GoogleMap googleMap;
    private Exception exception;
    private Context context;
    private ProgressDialog progressDialog;

    public GetDirectionsAsyncTask(GoogleMap googleMap, Context context, String directionMode) {
        super();
        this.googleMap = googleMap;
        this.context = context;
        this.DIRECTIONS_MODE = directionMode;
    }

    public void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        ElitelibUtility.showCustomProgressDialog(context, progressDialog, "", context.getString(R.string.progress), false);
    }

    @Override
    public void onPostExecute(ArrayList result) {

        ElitelibUtility.dismissCustomProgressDialog(progressDialog);

        if (exception == null) {
            handleGetDirectionsResult(result);
        } else {
            processException();
        }
    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);
        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }
        Polyline newPolyline = googleMap.addPolyline(rectLine);
    }


    @Override
    protected ArrayList doInBackground(Map<String, LatLng>... params) {
        Map<String, LatLng> paramMap = params[0];
        try {
            LatLng fromPosition = paramMap.get(CURRENT_LATLNG);
            LatLng toPosition = paramMap.get(DESTINATION_LATLNG);
            GMapV2Direction md = new GMapV2Direction();
            Document doc = md.getDocument(fromPosition, toPosition, DIRECTIONS_MODE);
            ArrayList directionPoints = md.getDirection(doc);
            return directionPoints;
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    private void processException() {
        Toast.makeText(context, context.getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
    }
}