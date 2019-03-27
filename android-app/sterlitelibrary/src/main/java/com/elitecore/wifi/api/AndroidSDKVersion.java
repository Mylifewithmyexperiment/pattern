package com.elitecore.wifi.api;

import android.os.Build;

/**
 * @author viratsinh.parmar
 *         AndroidSDKVersion class is declaration class for Android API version Constants.
 *         <p/>
 *         Class will also MAP SDK version with the name of the Android Version in enum variables.
 */
public interface AndroidSDKVersion {


    /**
     * This is for Current API version for the android.
     */
    public static final int CURRENT_API_VERSION = Build.VERSION.SDK_INT;

    /**
     * This is enum type to get the Human readable android version for the current android SDK API
     */
    public static enum Codenames {
        BASE, BASE_1,
        CUPCAKE,
        DONUT,
        ECLAIR, ECLAIR_MR1, ECLAIR_MR2,
        FROYO,
        GINGERBREAD, GINGERBREAD_MR1,
        HONEYCOMB, HONEYCOMB_MR1, HONEYCOMB_MR2,
        ICE_CREAM_SANDWICH, ICE_CREAM_SANDWICH_MR1,
        JELLY_BEAN, KITKAT, LOLLIPOP, MARSHMALLOW;

        public static Codenames getCodename() {
            int api = Build.VERSION.SDK_INT;

            switch (api) {
                case 1:

                    return BASE;
                case 2:

                    return BASE_1;
                case 3:

                    return CUPCAKE;
                case 4:

                    return DONUT;
                case 5:

                    return ECLAIR;
                case 6:

                    return ECLAIR_MR1;
                case 7:

                    return ECLAIR_MR2;
                case 8:

                    return FROYO;
                case 9:

                    return GINGERBREAD;
                case 10:

                    return GINGERBREAD_MR1;
                case 11:

                    return HONEYCOMB;
                case 12:

                    return HONEYCOMB_MR1;
                case 13:

                    return HONEYCOMB_MR2;
                case 14:

                    return ICE_CREAM_SANDWICH;
                case 15:

                    return ICE_CREAM_SANDWICH_MR1;
                case 16:

                    return JELLY_BEAN;
                case 17:

                    return JELLY_BEAN;
                case 18:

                    return JELLY_BEAN;
                case 19:

                    return KITKAT;

                case 20:

                    return KITKAT;

                case 21:

                    return LOLLIPOP;
                case 22:
                    return LOLLIPOP;
                case 23:
                    return MARSHMALLOW;
                default:
                    return null;
            }
        }

    }

    ;
}
