/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 7 Sep, 2018 6:27:19 PM
 * ----------------------------------------------------------------
 *
 * [y] hybris Platform
 * 
 * Copyright (c) 2000-2016 SAP SE
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of SAP 
 * Hybris ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.user;

import java.io.Serializable;

public  class UserSignUpWsDTO  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>UserSignUpWsDTO.uid</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String uid;

	/** <i>Generated property</i> for <code>UserSignUpWsDTO.firstName</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String firstName;

	/** <i>Generated property</i> for <code>UserSignUpWsDTO.lastName</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String lastName;

	/** <i>Generated property</i> for <code>UserSignUpWsDTO.titleCode</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String titleCode;

	/** <i>Generated property</i> for <code>UserSignUpWsDTO.password</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String password;

	/** Customer Email ID<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.email</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String email;

	/** Name Of the Customer<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.name</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String name;

	/** Used to track the life cycle status, e.g. registered ,
				Active<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.status</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String status;

	/** Description<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.description</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String description;

	/** channel by which customer is going to registered :
				Vodafone shop , Amazon , shopify etc.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.sourceChannel</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String sourceChannel;

	/** B2B Partner / web partner / Reseller<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.partnerID</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String partnerID;

	/** Name of Source system e.g Eshop<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.channelName</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String channelName;

	/**  Address Detail of customer<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.communicationAddress</code> property defined at extension <code>vfoccaddon</code>. */
		
	private AddressWsDTO communicationAddress;

	/** Circle Name<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.location</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String location;

	/** Salutation such as Mr. , Ms. , Dr. etc.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.title</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String title;

	/** Validation Of Customer.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.validFor</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String validFor;

	/** Product Code, in case selected on online channel.
			<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.product</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String product;

	/** Alternate Contact Number Of the Customer<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.mobileNumber</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String mobileNumber;

	/** DN Number.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.dnNumber</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String dnNumber;

	/** Activation category of customer. Possible values are
				IOIP, MNP or P2P.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.activationCategory</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String activationCategory;

	/** Need to provide weather prepaid customer or postpaid
				customer [in current scenario Need to pass default value =
				postpaid].<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.segment</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String segment;

	/** Selected Number during Online Journey.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.inventoryNumber</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryNumber;

	/** Open/Flexi field for future use.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.field1</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String field1;

	/** Open/Flexi field for future use.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.field2</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String field2;

	/** Open/Flexi field for future use.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.field3</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String field3;

	/** Open/Flexi field for future use.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.field4</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String field4;

	/** Open/Flexi field for future use.<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.field5</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String field5;

	/** UTM Parameters<br/><br/><i>Generated property</i> for <code>UserSignUpWsDTO.utmParameters</code> property defined at extension <code>vfoccaddon</code>. */
		
	private UTMParameterWsDTO utmParameters;
	
	public UserSignUpWsDTO()
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
	
		
	
	public void setTitleCode(final String titleCode)
	{
		this.titleCode = titleCode;
	}

		
	
	public String getTitleCode() 
	{
		return titleCode;
	}
	
		
	
	public void setPassword(final String password)
	{
		this.password = password;
	}

		
	
	public String getPassword() 
	{
		return password;
	}
	
		
	
	public void setEmail(final String email)
	{
		this.email = email;
	}

		
	
	public String getEmail() 
	{
		return email;
	}
	
		
	
	public void setName(final String name)
	{
		this.name = name;
	}

		
	
	public String getName() 
	{
		return name;
	}
	
		
	
	public void setStatus(final String status)
	{
		this.status = status;
	}

		
	
	public String getStatus() 
	{
		return status;
	}
	
		
	
	public void setDescription(final String description)
	{
		this.description = description;
	}

		
	
	public String getDescription() 
	{
		return description;
	}
	
		
	
	public void setSourceChannel(final String sourceChannel)
	{
		this.sourceChannel = sourceChannel;
	}

		
	
	public String getSourceChannel() 
	{
		return sourceChannel;
	}
	
		
	
	public void setPartnerID(final String partnerID)
	{
		this.partnerID = partnerID;
	}

		
	
	public String getPartnerID() 
	{
		return partnerID;
	}
	
		
	
	public void setChannelName(final String channelName)
	{
		this.channelName = channelName;
	}

		
	
	public String getChannelName() 
	{
		return channelName;
	}
	
		
	
	public void setCommunicationAddress(final AddressWsDTO communicationAddress)
	{
		this.communicationAddress = communicationAddress;
	}

		
	
	public AddressWsDTO getCommunicationAddress() 
	{
		return communicationAddress;
	}
	
		
	
	public void setLocation(final String location)
	{
		this.location = location;
	}

		
	
	public String getLocation() 
	{
		return location;
	}
	
		
	
	public void setTitle(final String title)
	{
		this.title = title;
	}

		
	
	public String getTitle() 
	{
		return title;
	}
	
		
	
	public void setValidFor(final String validFor)
	{
		this.validFor = validFor;
	}

		
	
	public String getValidFor() 
	{
		return validFor;
	}
	
		
	
	public void setProduct(final String product)
	{
		this.product = product;
	}

		
	
	public String getProduct() 
	{
		return product;
	}
	
		
	
	public void setMobileNumber(final String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}

		
	
	public String getMobileNumber() 
	{
		return mobileNumber;
	}
	
		
	
	public void setDnNumber(final String dnNumber)
	{
		this.dnNumber = dnNumber;
	}

		
	
	public String getDnNumber() 
	{
		return dnNumber;
	}
	
		
	
	public void setActivationCategory(final String activationCategory)
	{
		this.activationCategory = activationCategory;
	}

		
	
	public String getActivationCategory() 
	{
		return activationCategory;
	}
	
		
	
	public void setSegment(final String segment)
	{
		this.segment = segment;
	}

		
	
	public String getSegment() 
	{
		return segment;
	}
	
		
	
	public void setInventoryNumber(final String inventoryNumber)
	{
		this.inventoryNumber = inventoryNumber;
	}

		
	
	public String getInventoryNumber() 
	{
		return inventoryNumber;
	}
	
		
	
	public void setField1(final String field1)
	{
		this.field1 = field1;
	}

		
	
	public String getField1() 
	{
		return field1;
	}
	
		
	
	public void setField2(final String field2)
	{
		this.field2 = field2;
	}

		
	
	public String getField2() 
	{
		return field2;
	}
	
		
	
	public void setField3(final String field3)
	{
		this.field3 = field3;
	}

		
	
	public String getField3() 
	{
		return field3;
	}
	
		
	
	public void setField4(final String field4)
	{
		this.field4 = field4;
	}

		
	
	public String getField4() 
	{
		return field4;
	}
	
		
	
	public void setField5(final String field5)
	{
		this.field5 = field5;
	}

		
	
	public String getField5() 
	{
		return field5;
	}
	
		
	
	public void setUtmParameters(final UTMParameterWsDTO utmParameters)
	{
		this.utmParameters = utmParameters;
	}

		
	
	public UTMParameterWsDTO getUtmParameters() 
	{
		return utmParameters;
	}
	


}
