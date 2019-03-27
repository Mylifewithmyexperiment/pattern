package com.sterlite.dccmappfordealersterlite.model.dto.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elitecore on 12/9/18.
 */

public class OrderHistoryListWsDTO  implements Serializable
{

    /** Default serialVersionUID value. */

    private static final long serialVersionUID = 1L;

    /** <i>Generated property</i> for <code>OrderHistoryListWsDTO.orders</code> property defined at extension <code>commercewebservicescommons</code>. */

    private List<OrderHistoryWsDTO> orders;

    /** <i>Generated property</i> for <code>OrderHistoryListWsDTO.sorts</code> property defined at extension <code>commercewebservicescommons</code>. */

     /** <i>Generated property</i> for <code>OrderHistoryListWsDTO.pagination</code> property defined at extension <code>commercewebservicescommons</code>. */


    public OrderHistoryListWsDTO()
    {
        // default constructor
    }



    public void setOrders(final List<OrderHistoryWsDTO> orders)
    {
        this.orders = orders;
    }



    public List<OrderHistoryWsDTO> getOrders()
    {
        return orders;
    }


    @Override
    public String toString() {
        return "OrderHistoryListWsDTO{" +
                "orders=" + orders +
                '}';
    }
}
