package com.elitecorelib.andsf.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/22/2016.
 */
@RealmClass
public class ANDSFPolicies implements Comparable<ANDSFPolicies>,Serializable, RealmModel {
    public Integer policyId=0;
    public String policyName = "";
    public ANDSFValidityArea validityArea;
    public int rulePriority;
    public RealmList<ANDSFPrioritizedAccess> prioritizedAccess;
    public String PLMN = "";
    public boolean enable = false;
    public boolean roaming = false;
    public RealmList<ANDSFTimeOfDay> timeOfDay;
    public ANDSFExt ext;
    public String version = "";

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public ANDSFValidityArea getValidityArea() {
        return validityArea;
    }

    public void setValidityArea(ANDSFValidityArea validityArea) {
        this.validityArea = validityArea;
    }

    public int getRulePriority() {
        return rulePriority;
    }

    public void setRulePriority(int rulePriority) {
        this.rulePriority = rulePriority;
    }

    public RealmList<ANDSFPrioritizedAccess> getPrioritizedAccess() {
        return prioritizedAccess;
    }

    public void setPrioritizedAccess(RealmList<ANDSFPrioritizedAccess> prioritizedAccess) {
        this.prioritizedAccess = prioritizedAccess;
    }

    public String getPLMN() {
        return PLMN;
    }

    public void setPLMN(String PLMN) {
        this.PLMN = PLMN;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isRoaming() {
        return roaming;
    }

    public void setRoaming(boolean roaming) {
        this.roaming = roaming;
    }

    public RealmList<ANDSFTimeOfDay> getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(RealmList<ANDSFTimeOfDay> timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public ANDSFExt getExt() {
        return ext;
    }

    public void setExt(ANDSFExt ext) {
        this.ext = ext;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int compareTo(ANDSFPolicies another) {
        int returnVal = 0;
        if(this.rulePriority < another.rulePriority){
            returnVal = -1;
        }else if(this.rulePriority == another.rulePriority){
            returnVal = 0;
        }else{
            returnVal = 1;
        }
        return returnVal;
    }

}
