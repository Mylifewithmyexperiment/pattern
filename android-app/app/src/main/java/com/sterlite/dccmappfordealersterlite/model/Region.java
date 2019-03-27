package com.sterlite.dccmappfordealersterlite.model;

import java.util.Arrays;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;

/**
 * Created by elitecore on 28/9/18.
 */

public class Region {
    private String no;
    private String urban;
    private String subDistrict;
    private String municipality;
    private String province;
    private String pin;

    public Region() {
    }

    public static List<Region> getRegionList(){
        return Arrays.asList(AppUtils.getObjFromString(AppUtils.loadJSONFromAsset("region.json"), Region[].class));
    }

    public static Region getRegion(String pin){
        if(pin!=null && !pin.isEmpty()) {
            pin = pin.trim();
            List<Region> regions = Arrays.asList(AppUtils.getObjFromString(AppUtils.loadJSONFromAsset("region.json"), Region[].class));
            for (Region region : regions) {
                if(pin.equalsIgnoreCase(region.getPin())){
                    return region;
                }
            }
        }
        return new Region();
    }



    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUrban() {
        return urban;
    }

    public void setUrban(String urban) {
        this.urban = urban;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
