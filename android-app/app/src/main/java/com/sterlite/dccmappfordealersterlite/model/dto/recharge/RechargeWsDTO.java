package com.sterlite.dccmappfordealersterlite.model.dto.recharge;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by elitecore on 12/9/18.
 */

public class RechargeWsDTO implements Serializable {



    private static final long serialVersionUID = 1L;

    private String mobileNumber;

    private BigDecimal rechargeType;

    private Integer denominationAmount;

    private String sourceChannel;
}