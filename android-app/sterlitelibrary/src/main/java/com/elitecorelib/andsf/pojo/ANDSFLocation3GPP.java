package com.elitecorelib.andsf.pojo;

import android.util.Log;

import com.elitecorelib.andsf.exception.InvalidDataException;
import com.elitecorelib.andsf.utility.CustomConstant;
import com.elitecorelib.andsf.utility.CustomMessage;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/23/2016.
 */
@RealmClass
public class ANDSFLocation3GPP implements Serializable, RealmModel {
    public String LAC = "";
    public String TAC = "";
    public String GERAN_CI = "";
    public String UTRAN_CI = "";
    public String PLMN = "";
    public String EUTRA_CI = "";

    public String getLAC() {
        return LAC;
    }

    public void setLAC(String LAC) {
        this.LAC = LAC;
    }

    public String getTAC() {
        return TAC;
    }

    public void setTAC(String TAC) {
        this.TAC = TAC;
    }

    public String getGERAN_CI() {
        return GERAN_CI;
    }

    public void setGERAN_CI(String GERAN_CI) {
        this.GERAN_CI = GERAN_CI;
    }

    public String getUTRAN_CI() {
        return UTRAN_CI;
    }

    public void setUTRAN_CI(String UTRAN_CI) {
        this.UTRAN_CI = UTRAN_CI;
    }

    public String getPLMN() {
        return PLMN;
    }

    public void setPLMN(String PLMN) {
        this.PLMN = PLMN;
    }

    public String getEUTRA_CI() {
        return EUTRA_CI;
    }

    public void setEUTRA_CI(String EUTRA_CI) {
        this.EUTRA_CI = EUTRA_CI;
    }

    public void validate() throws InvalidDataException {

        if(this.PLMN == null || "".equals(this.PLMN)){
            Log.d(CustomConstant.ApplicationTag, "PLMN Should be mandatory if any 3GPP validity location is mentioned .");
            throw new InvalidDataException(CustomMessage.missingPLMN);
        }


    }
}
