package com.sterlite.dccmappfordealersterlite.model.dto.order;

import java.io.Serializable;
import java.util.Date;

import com.sterlite.dccmappfordealersterlite.model.dto.price.PriceWsDTO;

/**
 * Created by elitecore on 12/9/18.
 */

public class OrderHistoryWsDTO  implements Serializable
{

    /** Default serialVersionUID value. */

    private static final long serialVersionUID = 1L;

    /** <i>Generated property</i> for <code>OrderHistoryWsDTO.code</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String code;

    /** <i>Generated property</i> for <code>OrderHistoryWsDTO.status</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String status;

    /** <i>Generated property</i> for <code>OrderHistoryWsDTO.statusDisplay</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String statusDisplay;

    /** <i>Generated property</i> for <code>OrderHistoryWsDTO.placed</code> property defined at extension <code>commercewebservicescommons</code>. */

    private Date placed;

    /** <i>Generated property</i> for <code>OrderHistoryWsDTO.guid</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String guid;

    /** <i>Generated property</i> for <code>OrderHistoryWsDTO.total</code> property defined at extension <code>commercewebservicescommons</code>. */

    private PriceWsDTO total;

    public OrderHistoryWsDTO()
    {
        // default constructor
    }



    public void setCode(final String code)
    {
        this.code = code;
    }



    public String getCode()
    {
        return code;
    }



    public void setStatus(final String status)
    {
        this.status = status;
    }



    public String getStatus()
    {
        return status;
    }



    public void setStatusDisplay(final String statusDisplay)
    {
        this.statusDisplay = statusDisplay;
    }



    public String getStatusDisplay()
    {
        return statusDisplay;
    }



    public void setPlaced(final Date placed)
    {
        this.placed = placed;
    }



    public Date getPlaced()
    {
        return placed;
    }



    public void setGuid(final String guid)
    {
        this.guid = guid;
    }



    public String getGuid()
    {
        return guid;
    }



    public void setTotal(final PriceWsDTO total)
    {
        this.total = total;
    }



    public PriceWsDTO getTotal()
    {
        return total;
    }

    @Override
    public String toString() {
        return "OrderHistoryWsDTO{" +
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", statusDisplay='" + statusDisplay + '\'' +
                ", placed=" + placed +
                ", guid='" + guid + '\'' +
                ", total=" + total +
                '}';
    }
}