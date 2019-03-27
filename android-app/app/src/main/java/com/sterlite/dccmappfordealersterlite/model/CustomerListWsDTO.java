package com.sterlite.dccmappfordealersterlite.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elitecore on 24/12/18.
 */

public class CustomerListWsDTO implements Serializable {

    @JsonProperty("lstCustomer")
    private ArrayList<CustomerWsDTO> lstCustomer;



    public ArrayList<CustomerWsDTO> getLstCustomer() {

        return lstCustomer;
    }

    public void setLstCustomer(ArrayList<CustomerWsDTO> lstCustomer) {
        this.lstCustomer = lstCustomer;
    }

    @Override
    public String toString() {
        return "CustomerListWsDTO{" +
                "lstCustomer=" + lstCustomer +
                '}';
    }
}
