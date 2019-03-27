package com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart;

import java.io.Serializable;

/**
 * Created by elitecore on 14/9/18.
 */

public class AddToCartResponseWsDTO implements Serializable
{

    private static final long serialVersionUID = 1L;

    /** <i>Generated property</i> for <code>CartModificationWsDTO.statusCode</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String cartCode;

    private String responseCode;

    private String responseMessage;

    private boolean success;

    public String getCartCode() {
        return cartCode;
    }

    public void setCartCode(String cartCode) {
        this.cartCode = cartCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AddToCartResponseWsDTO{" +
                "cartCode='" + cartCode + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", success=" + success +
                '}';
    }
}
