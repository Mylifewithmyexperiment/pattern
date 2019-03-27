package com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Price implements Serializable {

    @JsonProperty("value")
    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return
                "Price{" +
                        "value = '" + value + '\'' +
                        "}";
    }
}