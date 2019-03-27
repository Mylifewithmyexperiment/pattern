package com.sterlite.dccmappfordealersterlite.model.customer;

/**
 * Created by elitecore on 11/10/18.
 */

import java.io.Serializable;

public class BaseRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ipAddress;

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String toString() {
        return "BaseRequest [ipAddress=" + this.ipAddress + "]";
    }
}
