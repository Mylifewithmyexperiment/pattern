package com.sterlite.dccmappfordealersterlite.model.dto.region;

import java.io.Serializable;

/**
 * Created by elitecore on 12/9/18.
 */

public class RegionWsDTO implements Serializable {


    private static final long serialVersionUID = 1L;


    private String isocode;


    private String isocodeShort;


    private String countryIso;


    private String name;

    public String getIsocode() {
        return isocode;
    }

    public String getIsocodeShort() {
        return isocodeShort;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public String getName() {
        return name;
    }

    public void setIsocode(String isocode) {
        this.isocode = isocode;
    }

    public void setIsocodeShort(String isocodeShort) {
        this.isocodeShort = isocodeShort;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RegionWsDTOmplements{" +
                "isocode='" + isocode + '\'' +
                ", isocodeShort='" + isocodeShort + '\'' +
                ", countryIso='" + countryIso + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}