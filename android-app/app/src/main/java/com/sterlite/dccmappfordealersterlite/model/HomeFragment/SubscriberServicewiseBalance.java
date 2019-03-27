package com.sterlite.dccmappfordealersterlite.model.HomeFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


public class SubscriberServicewiseBalance {

    @JsonProperty("balanceDatas")
    private List<BalanceData> balanceDatas = null;
    @JsonProperty("subscriberIdentifier")
    private String subscriberIdentifier;
    @JsonProperty("responseCode")
    private Integer responseCode;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("responseDescription")
    private Object responseDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    private String daysLeft;

    @JsonProperty("balanceDatas")
    public List<BalanceData> getBalanceDatas() {
        return balanceDatas;
    }

    @JsonProperty("balanceDatas")
    public void setBalanceDatas(List<BalanceData> balanceDatas) {
        this.balanceDatas = balanceDatas;
    }

    @JsonProperty("subscriberIdentifier")
    public String getSubscriberIdentifier() {
        return subscriberIdentifier;
    }

    @JsonProperty("subscriberIdentifier")
    public void setSubscriberIdentifier(String subscriberIdentifier) {
        this.subscriberIdentifier = subscriberIdentifier;
    }

    @JsonProperty("responseCode")
    public Integer getResponseCode() {
        return responseCode;
    }

    @JsonProperty("responseCode")
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    @JsonProperty("responseMessage")
    public String getResponseMessage() {
        return responseMessage;
    }

    @JsonProperty("responseMessage")
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @JsonProperty("responseDescription")
    public Object getResponseDescription() {
        return responseDescription;
    }

    @JsonProperty("responseDescription")
    public void setResponseDescription(Object responseDescription) {
        this.responseDescription = responseDescription;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(long daysLeft) {
        this.daysLeft = String.valueOf(daysLeft);
    }

    @Override
    public String toString() {
        return "SubscriberServicewiseBalance{" +
                "balanceDatas=" + balanceDatas +
                ", subscriberIdentifier='" + subscriberIdentifier + '\'' +
                ", responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                ", responseDescription=" + responseDescription +
                ", additionalProperties=" + additionalProperties +
                ", daysLeft='" + daysLeft + '\'' +
                '}';
    }
}