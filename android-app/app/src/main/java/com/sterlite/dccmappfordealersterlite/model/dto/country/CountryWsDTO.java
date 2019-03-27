package com.sterlite.dccmappfordealersterlite.model.dto.country;

import java.io.Serializable;

/**
 * Created by elitecore on 12/9/18.
 */

public class CountryWsDTO implements Serializable {

    /**
     * Default serialVersionUID value.
     */

    private static final long serialVersionUID = 1L;

    /**
     * <i>Generated property</i> for <code>CountryWsDTO.isocode</code> property defined at extension <code>commercewebservicescommons</code>.
     */

    private String isocode;

    /**
     * <i>Generated property</i> for <code>CountryWsDTO.name</code> property defined at extension <code>commercewebservicescommons</code>.
     */

    private String name;

    public CountryWsDTO() {
        // default constructor
    }


    public void setIsocode(final String isocode) {
        this.isocode = isocode;
    }


    public String getIsocode() {
        return isocode;
    }


    public void setName(final String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}