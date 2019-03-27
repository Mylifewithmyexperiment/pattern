package com.sterlite.dccmappfordealersterlite.model.customer;

/**
 * Created by elitecore on 11/10/18.
 */

import java.io.Serializable;

public class ResponseMessageDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long code;
    private String message;

    public ResponseMessageDetail(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseMessageDetail() {
    }

    public Long getCode() {
        return this.code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "ResponseMessageDetail [code=" + this.code + ", message=" + this.message + "]";
    }
}
