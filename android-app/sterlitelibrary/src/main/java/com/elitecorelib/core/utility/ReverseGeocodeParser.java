package com.elitecorelib.core.utility;

import android.location.Address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ReverseGeocodeParser {

    private ArrayList<Address> localArrayList;

    public ArrayList<Address> parseResponse(String paramString)
            throws JSONException {
        localArrayList = new ArrayList<Address>();
        if (paramString != null) {
            try {
                new Address(Locale.US);
                JSONArray localJSONArray = new JSONObject(paramString).getJSONArray("results");
                for (int i = 0; ; i++) {
                    if (i >= localJSONArray.length()) {
                        return localArrayList;
                    }
                    JSONObject localJSONObject1 = localJSONArray.getJSONObject(i);
                    JSONObject localJSONObject2 = localJSONObject1.getJSONObject("geometry").getJSONObject("location");
                    String str1 = localJSONObject1.getString("formatted_address");
                    String str2 = localJSONObject2.getString("lat");
                    String str3 = localJSONObject2.getString("lng");
                    Double localDouble1 = Double.valueOf(0.0D);
                    if (str2 != null) {
                        localDouble1 = new Double(str2);
                    }
                    Double localDouble2 = Double.valueOf(0.0D);
                    if (str3 != null) {
                        localDouble2 = new Double(str3);
                    }
                    Address localAddress = new Address(Locale.US);
                    localAddress.setLocality(str1);
                    localAddress.setLatitude(localDouble1.doubleValue());
                    localAddress.setLongitude(localDouble2.doubleValue());
                    localArrayList.add(localAddress);
                }

            } catch (JSONException localJSONException) {
                throw localJSONException;
            }
        }
        return localArrayList;
    }
}



