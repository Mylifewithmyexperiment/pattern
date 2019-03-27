package com.sterlite.dccmappfordealersterlite.model.dto.currency;

import java.io.Serializable;

/**
 * Created by elitecore on 12/9/18.
 */

public class CurrencyWsDTO implements Serializable
{

    /** Default serialVersionUID value. */

    private static final long serialVersionUID = 1L;

    /** <i>Generated property</i> for <code>CurrencyWsDTO.isocode</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String isocode;

    /** <i>Generated property</i> for <code>CurrencyWsDTO.name</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String name;

    /** <i>Generated property</i> for <code>CurrencyWsDTO.active</code> property defined at extension <code>commercewebservicescommons</code>. */

    private Boolean active;

    /** <i>Generated property</i> for <code>CurrencyWsDTO.symbol</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String symbol;

    public CurrencyWsDTO()
    {
        // default constructor
    }



    public void setIsocode(final String isocode)
    {
        this.isocode = isocode;
    }



    public String getIsocode()
    {
        return isocode;
    }



    public void setName(final String name)
    {
        this.name = name;
    }



    public String getName()
    {
        return name;
    }



    public void setActive(final Boolean active)
    {
        this.active = active;
    }



    public Boolean getActive()
    {
        return active;
    }



    public void setSymbol(final String symbol)
    {
        this.symbol = symbol;
    }



    public String getSymbol()
    {
        return symbol;
    }



}

