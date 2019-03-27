package com.elitecorelib.core.pojo;

import java.io.Serializable;
import java.util.Set;

public class PojoWiFiProfiles implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2224141050632087879L;

    private Long androidSettingId;
    private String androidSettingName;
    private String status;
    private String readOnlySetting;
    private String removeAllowFromApp;
    private String isPreferable;
    private String description;
    //	private Timestamp createDate;
    //private Long createdByStaffId;
    //private Timestamp lastModifiedDate;
    //private Long lastModifiedByStaffId;

    private Set<PojoWiFiConnections> wifiSettingSet;


    public Set<PojoWiFiConnections> getWifiSettingSet() {
        return wifiSettingSet;
    }

    public void setWifiSettingSet(Set<PojoWiFiConnections> wifiSettingSet) {
        this.wifiSettingSet = wifiSettingSet;
    }

    public Long getAndroidSettingId() {
        return androidSettingId;
    }

    public void setAndroidSettingId(Long androidSettingId) {
        this.androidSettingId = androidSettingId;
    }

    public String getAndroidSettingName() {
        return androidSettingName;
    }

    public void setAndroidSettingName(String androidSettingName) {
        this.androidSettingName = androidSettingName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReadOnlySetting() {
        return readOnlySetting;
    }

    public void setReadOnlySetting(String readOnlySetting) {
        this.readOnlySetting = readOnlySetting;
    }

    public String getRemoveAllowFromApp() {
        return removeAllowFromApp;
    }

    public void setRemoveAllowFromApp(String removeAllowFromApp) {
        this.removeAllowFromApp = removeAllowFromApp;
    }

    public String getIsPreferable() {
        return isPreferable;
    }

    public void setIsPreferable(String isPreferable) {
        this.isPreferable = isPreferable;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((androidSettingId == null) ? 0 : androidSettingId.hashCode());
        result = prime * result + ((androidSettingName == null) ? 0 : androidSettingName.hashCode());
        result = prime * result + ((readOnlySetting == null) ? 0 : readOnlySetting.hashCode());
        result = prime * result + ((removeAllowFromApp == null) ? 0 : removeAllowFromApp.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PojoWiFiProfiles other = (PojoWiFiProfiles) obj;
        if (androidSettingId == null) {
            if (other.androidSettingId != null)
                return false;
        } else if (!androidSettingId.equals(other.androidSettingId))
            return false;
        if (androidSettingName == null) {
            if (other.androidSettingName != null)
                return false;
        } else if (!androidSettingName.equals(other.androidSettingName))
            return false;
        if (readOnlySetting == null) {
            if (other.readOnlySetting != null)
                return false;
        } else if (!readOnlySetting.equals(other.readOnlySetting))
            return false;
        if (removeAllowFromApp == null) {
            if (other.removeAllowFromApp != null)
                return false;
        } else if (!removeAllowFromApp.equals(other.removeAllowFromApp))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        return true;
    }


}
