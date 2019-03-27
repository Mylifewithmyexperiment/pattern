package com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MakeMyPlanItems implements Serializable {

    @JsonProperty("availableForPickup")
    private boolean availableForPickup;

    @JsonProperty("code")
    private String code;

    @JsonProperty("numberOfReviews")
    private int numberOfReviews;

    @JsonProperty("price")
    private Price price;

    @JsonProperty("oneTimeProductPrice")
    private OneTimeProductPrice oneTimeProductPrice;

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    private String categoryCode;
    private MMPBase arrDataAddOn;
    private MMPBase arrDataSubAddOn;
    private MMPBase arrMinAddOn;
    private MMPBase arrSMSAddOn;
    private MMPBase arrRoamingAddOn;


    private ArrayList<MMPBase> arrUpToBenefits;

    public ArrayList<MMPBase> getArrUpToBenefits() {
        return arrUpToBenefits;
    }

    public void setArrUpToBenefits(ArrayList<MMPBase> arrUpToBenefits) {
        this.arrUpToBenefits = arrUpToBenefits;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public MMPBase getArrDataAddOn() {
        return arrDataAddOn;
    }

    public void setArrDataAddOn(MMPBase arrDataAddOn) {
        this.arrDataAddOn = arrDataAddOn;
    }

    public MMPBase getArrDataSubAddOn() {
        return arrDataSubAddOn;
    }

    public void setArrDataSubAddOn(MMPBase arrDataSubAddOn) {
        this.arrDataSubAddOn = arrDataSubAddOn;
    }

    public MMPBase getArrMinAddOn() {
        return arrMinAddOn;
    }

    public void setArrMinAddOn(MMPBase arrMinAddOn) {
        this.arrMinAddOn = arrMinAddOn;
    }

    public MMPBase getArrSMSAddOn() {
        return arrSMSAddOn;
    }

    public void setArrSMSAddOn(MMPBase arrSMSAddOn) {
        this.arrSMSAddOn = arrSMSAddOn;
    }

    public MMPBase getArrRoamingAddOn() {
        return arrRoamingAddOn;
    }

    public void setArrRoamingAddOn(MMPBase arrRoamingAddOn) {
        this.arrRoamingAddOn = arrRoamingAddOn;
    }

    public void setAvailableForPickup(boolean availableForPickup) {
        this.availableForPickup = availableForPickup;
    }

    public boolean isAvailableForPickup() {
        return availableForPickup;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public OneTimeProductPrice getOneTimeProductPrice() {
        return oneTimeProductPrice;
    }

    public void setOneTimeProductPrice(OneTimeProductPrice oneTimeProductPrice) {
        this.oneTimeProductPrice = oneTimeProductPrice;
    }

//    @Override
//    public String toString() {
//        return "MakeMyPlanItems{" +
//                "availableForPickup=" + availableForPickup +
//                ", code='" + code + '\'' +
//                ", numberOfReviews=" + numberOfReviews +
//                ", price=" + price +
//                ", oneTimeProductPrice=" + oneTimeProductPrice +
//                ", name='" + name + '\'' +
//                ", url='" + url + '\'' +
//                '}';
//    }

    /*@Override
        public String toString() {
            return
                    "MakeMyPlanItems{" +
                            "availableForPickup = '" + availableForPickup + '\'' +
                            ",code = '" + code + '\'' +
                            ",numberOfReviews = '" + numberOfReviews + '\'' +
                            ",price = '" + price + '\'' +
                            ",name = '" + name + '\'' +
                            ",url = '" + url + '\'' +
                            "}";
        }
    */
    public static ArrayList<MakeMyPlanItems> parseValueFromServer(JSONObject jsonObject, String arrayName) {
        ArrayList<MakeMyPlanItems> list = new ArrayList<>();
        try {
            JSONArray array = jsonObject.getJSONArray(arrayName);
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            list = mapper.readValue(array.toString(), new TypeReference<ArrayList<MakeMyPlanItems>>() {
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public String toString() {
        return "MakeMyPlanItems{" +
                "availableForPickup=" + availableForPickup +
                ", code='" + code + '\'' +
                ", numberOfReviews=" + numberOfReviews +
                ", price=" + price +
                ", oneTimeProductPrice=" + oneTimeProductPrice +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", arrDataAddOn=" + arrDataAddOn +
                ", arrDataAddSubOn=" + arrDataSubAddOn +
                ", arrMinAddOn=" + arrMinAddOn +
                ", arrSMSAddOn=" + arrSMSAddOn +
                ", arrRoamingAddOn=" + arrRoamingAddOn +
                ", arrUpToBenefits=" + arrUpToBenefits +
                '}';
    }
}