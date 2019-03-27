package com.sterlite.dccmappfordealersterlite.model.UpdateCustomer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by elitecore on 26/3/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUpdateCustomerRequestData implements Serializable {


    @JsonProperty("customerNumber")
    private String customerNumber;

    @JsonProperty("otherPhone")
    private String otherPhone;

    @JsonProperty("otherEmail")
    private String otherEmail;

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getOtherEmail() {
        return otherEmail;
    }

    public void setOtherEmail(String otherEmail) {
        this.otherEmail = otherEmail;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GetUpdateCustomerRequestData{");
        sb.append("customerNumber='").append(customerNumber).append('\'');
        sb.append(", otherPhone='").append(otherPhone).append('\'');
        sb.append(", otherEmail='").append(otherEmail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}


