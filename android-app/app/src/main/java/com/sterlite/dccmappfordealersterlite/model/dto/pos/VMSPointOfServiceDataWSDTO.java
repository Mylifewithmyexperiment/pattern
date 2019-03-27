package com.sterlite.dccmappfordealersterlite.model.dto.pos;

import java.io.Serializable;

/**
 * Created by elitecore on 13/9/18.
 */

public class VMSPointOfServiceDataWSDTO implements Serializable{


    private String responseCode;

    /** <i>Generated property</i> for <code>VMSPointOfServiceDataWSDTO.responseMessage</code> property defined at extension <code>vfoccaddon</code>. */

    private String responseMessage;

    public String getResponseMessage() {

        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {

        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }


    @Override
    public String toString() {
        return "VMSPointOfServiceDataWSDTO{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}
