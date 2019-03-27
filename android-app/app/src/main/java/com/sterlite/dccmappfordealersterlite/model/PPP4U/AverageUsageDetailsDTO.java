package com.sterlite.dccmappfordealersterlite.model.PPP4U;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AverageUsageDetailsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("dataAmazonprimeUsage")
    String dataAmazonprimeUsage;
    @JsonProperty("dataMBUsage")
    String dataMBUsage;
    @JsonProperty("dataNetflixUsage")
    String dataNetflixUsage;
    @JsonProperty("rechargeAmount")
    String rechargeAmount;
    @JsonProperty("voiceMinUsage")
    String voiceMinUsage;
    @JsonProperty("dataYoutubeUsage")
    String dataYoutubeUsage;

    public String getDataYoutubeUsage() {
        return dataYoutubeUsage;
    }

    public void setDataYoutubeUsage(String dataYoutubeUsage) {
        this.dataYoutubeUsage = dataYoutubeUsage;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getVoiceMinUsage() {
        return voiceMinUsage;
    }

    public void setVoiceMinUsage(String voiceMinUsage) {
        this.voiceMinUsage = voiceMinUsage;
    }

    public AverageUsageDetailsDTO() {
    }

    public String getDataAmazonprimeUsage() {
        return dataAmazonprimeUsage;
    }

    public void setDataAmazonprimeUsage(String dataAmazonprimeUsage) {
        this.dataAmazonprimeUsage = dataAmazonprimeUsage;
    }

    public String getDataMBUsage() {
        return dataMBUsage;
    }

    public void setDataMBUsage(String dataMBUsage) {
        this.dataMBUsage = dataMBUsage;
    }

    public String getDataNetflixUsage() {
        return dataNetflixUsage;
    }

    public void setDataNetflixUsage(String dataNetflixUsage) {
        this.dataNetflixUsage = dataNetflixUsage;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
