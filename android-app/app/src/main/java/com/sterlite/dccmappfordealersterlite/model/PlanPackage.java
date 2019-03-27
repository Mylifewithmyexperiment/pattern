package com.sterlite.dccmappfordealersterlite.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.OneTimeProductPrice;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.Price;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanPackage {

    @JsonProperty("availableForPickup")
    private boolean availableForPickup;

    @JsonProperty("code")
    private String code;

    @JsonProperty("numberOfReviews")
    private int numberOfReviews;

    @JsonProperty("price")
    private Price price;

    @JsonProperty("name")
    private String name;


    @JsonProperty("url")
    private String url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("oneTimeProductPrice")
    private OneTimeProductPrice oneTimeProductPrice;


    private String leftMsg;
    private String topMsg;

    public OneTimeProductPrice getOneTimeProductPrice() {
        return oneTimeProductPrice;
    }

    public void setOneTimeProductPrice(OneTimeProductPrice oneTimeProductPrice) {
        this.oneTimeProductPrice = oneTimeProductPrice;
    }

    public String getLeftMsg() {
        return leftMsg;
    }

    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
    }

    public String getTopMsg() {
        return topMsg;
    }

    public void setTopMsg(String topMsg) {
        this.topMsg = topMsg;
    }

    public boolean isAvailableForPickup() {
        return availableForPickup;
    }

    public void setAvailableForPickup(boolean availableForPickup) {
        this.availableForPickup = availableForPickup;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public static ArrayList<PlanPackage> parseValueFromServer(JSONObject jsonObject, String arrayName) {
        ArrayList<PlanPackage> list = new ArrayList<>();
        try {
            JSONArray array = jsonObject.getJSONArray(arrayName);
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            list = mapper.readValue(array.toString(), new TypeReference<ArrayList<PlanPackage>>() {
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<PlanPackage> sortArray(ArrayList<PlanPackage> planItems) {
        Collections.sort(planItems, new Comparator<PlanPackage>() {
            @Override
            public int compare(PlanPackage o1, PlanPackage o2) {
                return o1.getOneTimeProductPrice().getValue() - o2.getOneTimeProductPrice().getValue();
            }
        });
        return planItems;
    }
}
