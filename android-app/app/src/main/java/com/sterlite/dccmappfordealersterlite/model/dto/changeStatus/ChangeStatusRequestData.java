package com.sterlite.dccmappfordealersterlite.model.dto.changeStatus;

import java.io.Serializable;

/**
 * Created by elitecore on 14/9/18.
 */

public class ChangeStatusRequestData implements Serializable {
    private String newStatusID;
    private String remarks;
    private String serviceInstanceNumner;

    public void setNewStatusID(String newStatusID) {
        this.newStatusID = newStatusID;
    }

    public String getNewStatusID() {
        return this.newStatusID;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setServiceInstanceNumner(String serviceInstanceNumner) {
        this.serviceInstanceNumner = serviceInstanceNumner;
    }

    public String getServiceInstanceNumner() {
        return this.serviceInstanceNumner;
    }
}
