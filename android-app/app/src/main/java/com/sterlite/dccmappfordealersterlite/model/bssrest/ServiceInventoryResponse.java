package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

/**
 * Created by elitecore on 12/10/18.
 */

public class ServiceInventoryResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    String inventoryNumber;
    String serviceInstanceNumber;
    String userName;
    String planName;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getServiceInstanceNumber() {
        return serviceInstanceNumber;
    }

    public void setServiceInstanceNumber(String serviceInstanceNumber) {
        this.serviceInstanceNumber = serviceInstanceNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return planName;
    }
}
