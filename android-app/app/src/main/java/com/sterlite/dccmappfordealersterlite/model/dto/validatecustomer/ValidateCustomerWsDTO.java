package com.sterlite.dccmappfordealersterlite.model.dto.validatecustomer;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.dto.region.RegionWsDTO;


/**
 * Created by elitecore on 12/9/18.
 */

public class ValidateCustomerWsDTO  implements Serializable {

    private static final long serialVersionUID = 1L;
    private String email;

    /** Alternate Contact Number Of the Customer<br/><br/><i>Generated property</i> for <code>ValidateCustomerWsDTO.mobileNumber</code> property defined at extension <code>vfoccaddon</code>. */

    private String mobileNumber;

    /**  Region Detail of customer<br/><br/><i>Generated property</i> for <code>ValidateCustomerWsDTO.region</code> property defined at extension <code>vfoccaddon</code>. */

    private RegionWsDTO region;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public RegionWsDTO getRegion() {
        return region;
    }

    public void setRegion(RegionWsDTO region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "ValidateCustomerWsDTO{" +
                "email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", region=" + region +
                '}';
    }
}
