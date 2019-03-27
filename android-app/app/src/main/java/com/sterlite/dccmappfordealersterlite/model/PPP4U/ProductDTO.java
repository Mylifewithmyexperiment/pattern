package com.sterlite.dccmappfordealersterlite.model.PPP4U;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.Plan;
import com.sterlite.dccmappfordealersterlite.model.PlanPackage;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("categoryCode")
    private String categoryCode;
    @JsonProperty("products")
    private List<PlanPackage> products;

    private List<Plan> generatedPlans=new ArrayList<>();


    public ProductDTO()
    {
        // default constructor
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public List<PlanPackage> getProducts() {
        return products;
    }

    public void setProducts(List<PlanPackage> products) {
        this.products = products;
    }

    public List<Plan> getGeneratedPlans() {
        return generatedPlans;
    }

    public void setGeneratedPlans(List<Plan> generatedPlans) {
        this.generatedPlans = generatedPlans;
    }
}
