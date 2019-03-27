package com.sterlite.dccmappfordealersterlite.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by elitecore on 24/12/18.
 */

public class CustomerWsDTO implements Serializable {

    @JsonProperty("customerId")
    private String customerId;

    /** <i>Generated property</i> for <code>CustomerWsDTO.firstName</code> property defined at extension <code>vfoccaddon</code>. */

    @JsonProperty("firstName")
    private String firstName;

    /** <i>Generated property</i> for <code>CustomerWsDTO.lastName</code> property defined at extension <code>vfoccaddon</code>. */


    /** <i>Generated property</i> for <code>CustomerWsDTO.email</code> property defined at extension <code>vfoccaddon</code>. */

    @JsonProperty("email")
    private String email;

    /** <i>Generated property</i> for <code>CustomerWsDTO.mobileNumber</code> property defined at extension <code>vfoccaddon</code>. */

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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

    @Override
    public String toString() {
        return "CustomerWsDTO{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
