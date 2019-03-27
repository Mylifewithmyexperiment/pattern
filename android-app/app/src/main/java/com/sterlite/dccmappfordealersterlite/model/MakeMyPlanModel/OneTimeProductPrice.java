package com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel;


        import org.codehaus.jackson.annotate.JsonIgnoreProperties;
        import org.codehaus.jackson.annotate.JsonProperty;

        import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OneTimeProductPrice implements Serializable {

   /* OneTimeProductPrice" : {
            "currencyIso" : "IDR",
            "formattedValue" : "Rp30,000.00",
            "priceType" : "BUY",
            "value" : 30000.0
}*/

    @JsonProperty("currencyIso")
    private String currencyIso;
    @JsonProperty("formattedValue")
    private String formattedValue;
    @JsonProperty("priceType")
    private String priceType;
    @JsonProperty("value")
    private int value;


    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public String getFormattedValue() {
        return formattedValue;
    }

    public void setFormattedValue(String formattedValue) {
        this.formattedValue = formattedValue;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }


    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

//    @Override
//    public String toString() {
//        return
//                "Price{" +
//                        "value = '" + value + '\'' +
//                        "}";
//    }


    @Override
    public String toString() {
        return "OneTimeProductPrice{" +
                "currencyIso='" + currencyIso + '\'' +
                ", formattedValue='" + formattedValue + '\'' +
                ", priceType='" + priceType + '\'' +
                ", value=" + value +
                '}';
    }
}