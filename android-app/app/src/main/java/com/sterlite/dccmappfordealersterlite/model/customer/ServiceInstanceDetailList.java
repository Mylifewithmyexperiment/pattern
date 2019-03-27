package com.sterlite.dccmappfordealersterlite.model.customer;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInstanceDetail;

/**
 * Created by elitecore on 12/10/18.
 */

public class ServiceInstanceDetailList implements Serializable {

    private static final long serialVersionUID = 1L;


    private List<ServiceInstanceDetail> serviceInstanceDetail;

    public List<ServiceInstanceDetail> getServiceInstanceDetail() {
        return serviceInstanceDetail;
    }

    public void setServiceInstanceDetail(List<ServiceInstanceDetail> serviceInstanceDetail) {
        this.serviceInstanceDetail = serviceInstanceDetail;
    }

    @Override
    public String toString() {
        return "ServiceInstanceDetailList{" +
                "serviceInstanceDetail=" + serviceInstanceDetail +
                '}';
    }
}
