package com.sterlite.dccmappfordealersterlite.model.PPP4U;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostPaidPlansForYouDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("averageUsageDetails")
    AverageUsageDetailsDTO averageUsageDetails;
    @JsonProperty("product")
    ProductDTO product;

    public PostPaidPlansForYouDTO() {
    }

    public AverageUsageDetailsDTO getAverageUsageDetails() {
        return averageUsageDetails;
    }

    public void setAverageUsageDetails(AverageUsageDetailsDTO averageUsageDetails) {
        this.averageUsageDetails = averageUsageDetails;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
