package com.elitecorelib.core.utility;

import android.content.Context;
import android.content.Intent;

import com.elitecorelib.core.EliteSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActivityUtils {
    private static final String MODULE = "ActivityUtils";

    private static Class getActivityClass(Class clazz) {
        Class extClass = null;
        // Check for extended activity
        String extClassName = clazz.getName() + "Extended";
        try {
            extClass = Class.forName(extClassName);
            //return extClass;
        } catch (ClassNotFoundException e) {
            //Log.e(MODULE, e.getMessage());
            //e.printStackTrace();
            return clazz;
        } catch (Exception e) {
            //Log.e(MODULE,"second catch");
            //EliteSession.eLog.e(MODULE, e.getMessage());
            //
            return clazz;
        }
        return extClass;
    }

    public static Intent createIntent(Context context, Class clazz) {
        Class activityClass = getActivityClass(clazz);
        return new Intent(context, activityClass);
    }

    public static Intent createIntentFromResourceId(Context context, int id) {
        try {
            Class resourceUtility = Class.forName("com.elitecore.easyconnect.utility.ResourceUtility");
            Class[] classes = new Class[2];
            classes[0] = Context.class;
            classes[1] = Integer.class;
            Method method = resourceUtility.getDeclaredMethod("getIntentById", classes);
            return (Intent) method.invoke(resourceUtility.newInstance(), new Object[]{context, Integer.valueOf(id)});
        } catch (ClassNotFoundException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } catch (SecurityException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } catch (NoSuchMethodException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } catch (IllegalArgumentException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } catch (IllegalAccessException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } catch (InvocationTargetException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } catch (InstantiationException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
        return null;
    }


}
