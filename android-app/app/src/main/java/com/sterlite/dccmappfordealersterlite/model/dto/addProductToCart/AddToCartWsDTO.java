package com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart;

import java.io.Serializable;

/**
 * Created by elitecore on 14/9/18.
 */

public class AddToCartWsDTO implements Serializable
{

    private static final long serialVersionUID = 1L;

    /** <i>Generated property</i> for <code>CartModificationWsDTO.statusCode</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String productCode;
    private String bundledTemplateID;
    private String productCategory;

    private boolean removeOlderEntries;

    public String getBundledTemplateID() {
        return bundledTemplateID;
    }

    public void setBundledTemplateID(String bundledTemplateID) {
        this.bundledTemplateID = bundledTemplateID;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public boolean isRemoveOlderEntries() {
        return removeOlderEntries;
    }

    public void setRemoveOlderEntries(boolean removeOlderEntries) {
        this.removeOlderEntries = removeOlderEntries;
    }
}
