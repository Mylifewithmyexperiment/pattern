package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;

/**
 * Created by elitecore on 31/10/18.
 */

import java.io.Serializable;

public class OnlineMethodData implements Serializable {
    private String paymentMode;
    private String instituteBranchName;
    private long instituteMasterId;
    private String instituteMasterName;

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentMode() {
        return this.paymentMode;
    }

    public void setInstituteBranchName(String instituteBranchName) {
        this.instituteBranchName = instituteBranchName;
    }

    public String getInstituteBranchName() {
        return this.instituteBranchName;
    }

    public void setInstituteMasterId(long instituteMasterId) {
        this.instituteMasterId = instituteMasterId;
    }

    public long getInstituteMasterId() {
        return this.instituteMasterId;
    }

    public void setInstituteMasterName(String instituteMasterName) {
        this.instituteMasterName = instituteMasterName;
    }

    public String getInstituteMasterName() {
        return this.instituteMasterName;
    }
}