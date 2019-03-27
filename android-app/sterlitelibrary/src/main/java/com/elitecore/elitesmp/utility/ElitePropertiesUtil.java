package com.elitecore.elitesmp.utility;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by salmankhan.yusufjai on 9/10/2015.
 */
public class ElitePropertiesUtil {

    private static volatile ElitePropertiesUtil elitePropertiesUtil;
    private String MODULE = "ElitePropertiesUtil";
    private Properties eliteSMPProperites = new Properties();

    public static ElitePropertiesUtil createInstance(int resourceID) {
        if (elitePropertiesUtil == null) {
            elitePropertiesUtil = new ElitePropertiesUtil();
            try {
                elitePropertiesUtil.intializeProperties(resourceID);
            } catch (Exception e) {
                EliteSession.eLog.d(e.getMessage());
            }
        }
        return elitePropertiesUtil;
    }

    public static ElitePropertiesUtil getInstance() {
        return elitePropertiesUtil;
    }

    private void intializeProperties(int resourceId) throws Exception {

        try {
            InputStream is = null;
            is = LibraryApplication.getLibraryApplication().getLibraryContext().getResources().openRawResource(resourceId);
            if (eliteSMPProperites == null) {
                throw new Exception("Elite SMP properties not initialized or invalid property file.");
            }
            eliteSMPProperites.load(is);
        } catch (Exception e) {
            throw new Exception("Elite SMP properties not initialized or invalid property file.");
        }
    }

    public String getConfigProperty(String key) {
        try {
            if(eliteSMPProperites.getProperty(key)==null)
                EliteSession.eLog.d(MODULE,"Key "+key +" is not defined in elitesmp property file of the raw folder in application");
            return eliteSMPProperites.getProperty(key);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE,"Key "+key +" is not defined in elitesmp property file of the raw folder in application");
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
        return null;
    }

    public void setConfigProperty(String key,String value) {
        try {
            eliteSMPProperites.put(key,value);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE,"Key "+key +" is not defined in elitesmp property file of the raw folder in application");
            EliteSession.eLog.e(MODULE, e.getMessage());

        }

    }

}
