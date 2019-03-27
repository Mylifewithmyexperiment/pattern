package com.sterlite.dccmappfordealersterlite.model.dto.user;

import java.io.Serializable;

/**
 * Created by elitecore on 12/9/18.
 */

public class PrincipalWsDTO implements Serializable
{

    /** Default serialVersionUID value. */

    private static final long serialVersionUID = 1L;

    /** <i>Generated property</i> for <code>PrincipalWsDTO.uid</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String uid;

    /** <i>Generated property</i> for <code>PrincipalWsDTO.name</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String name;

    public PrincipalWsDTO()
    {
        // default constructor
    }



    public void setUid(final String uid)
    {
        this.uid = uid;
    }



    public String getUid()
    {
        return uid;
    }



    public void setName(final String name)
    {
        this.name = name;
    }



    public String getName()
    {
        return name;
    }



}
