package com.sterlite.dccmappfordealersterlite.model.dto.user;

import com.sterlite.dccmappfordealersterlite.model.dto.address.AddressWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.currency.CurrencyWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.language.LanguageWsDTO;

/**
 * Created by elitecore on 12/9/18.
 */

public class UserWsDTO extends  PrincipalWsDTO{

    /** <i>Generated property</i> for <code>UserWsDTO.defaultAddress</code> property defined at extension <code>commercewebservicescommons</code>. */

    private AddressWsDTO defaultAddress;

    /** <i>Generated property</i> for <code>UserWsDTO.titleCode</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String titleCode;

    /** <i>Generated property</i> for <code>UserWsDTO.title</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String title;

    /** <i>Generated property</i> for <code>UserWsDTO.firstName</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String firstName;

    /** <i>Generated property</i> for <code>UserWsDTO.lastName</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String lastName;

    /** <i>Generated property</i> for <code>UserWsDTO.currency</code> property defined at extension <code>commercewebservicescommons</code>. */

    private CurrencyWsDTO currency;

    /** <i>Generated property</i> for <code>UserWsDTO.language</code> property defined at extension <code>commercewebservicescommons</code>. */

    private LanguageWsDTO language;

    /** <i>Generated property</i> for <code>UserWsDTO.displayUid</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String displayUid;

    /** <i>Generated property</i> for <code>UserWsDTO.customerId</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String customerId;

    public UserWsDTO()
    {
        // default constructor
    }



    public void setDefaultAddress(final AddressWsDTO defaultAddress)
    {
        this.defaultAddress = defaultAddress;
    }



    public AddressWsDTO getDefaultAddress()
    {
        return defaultAddress;
    }



    public void setTitleCode(final String titleCode)
    {
        this.titleCode = titleCode;
    }



    public String getTitleCode()
    {
        return titleCode;
    }



    public void setTitle(final String title)
    {
        this.title = title;
    }



    public String getTitle()
    {
        return title;
    }



    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }



    public String getFirstName()
    {
        return firstName;
    }



    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
    }



    public String getLastName()
    {
        return lastName;
    }



    public void setCurrency(final CurrencyWsDTO currency)
    {
        this.currency = currency;
    }



    public CurrencyWsDTO getCurrency()
    {
        return currency;
    }



    public void setLanguage(final LanguageWsDTO language)
    {
        this.language = language;
    }



    public LanguageWsDTO getLanguage()
    {
        return language;
    }



    public void setDisplayUid(final String displayUid)
    {
        this.displayUid = displayUid;
    }



    public String getDisplayUid()
    {
        return displayUid;
    }



    public void setCustomerId(final String customerId)
    {
        this.customerId = customerId;
    }



    public String getCustomerId()
    {
        return customerId;
    }

    @Override
    public String toString() {
        return "UserWsDTO{" +
                "defaultAddress=" + defaultAddress +
                ", titleCode='" + titleCode + '\'' +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", currency=" + currency +
                ", language=" + language +
                ", displayUid='" + displayUid + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}

