package com.sterlite.dccmappfordealersterlite.model.UpdateCustomer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by elitecore on 26/3/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUpdateCustomerResponseData implements Serializable {


    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("customerAccountNumber")
    private String customerAccountNumber;

    @JsonProperty("otherPhone")
    private String otherPhone;

    @JsonProperty("otherEmail")
    private String otherEmail;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public void setCustomerAccountNumber(String customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
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
        final StringBuffer sb = new StringBuffer("GetUpdateCustomerResponseData{");
        sb.append("responseCode='").append(responseCode).append('\'');
        sb.append(", responseMessage='").append(responseMessage).append('\'');
        sb.append(", customerAccountNumber='").append(customerAccountNumber).append('\'');
        sb.append(", otherPhone='").append(otherPhone).append('\'');
        sb.append(", otherEmail='").append(otherEmail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}