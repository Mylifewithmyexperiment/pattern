package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class AccountNotificationDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String accountNumber;
	private List<NotificationReference> notificationRef;
	private List<ContactMedium> contactMedium;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public List<NotificationReference> getNotificationRef() {
		return notificationRef;
	}
	
	public void setNotificationRef(List<NotificationReference> notificationRef) {
		this.notificationRef = notificationRef;
	}
	
	public List<ContactMedium> getContactMedium() {
		return contactMedium;
	}
	
	public void setContactMedium(List<ContactMedium> contactMedium) {
		this.contactMedium = contactMedium;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(80);
		builder.append("AccountNotificationDetail [accountNumber=").append(accountNumber).append(", notificationRef=")
				.append(notificationRef).append(", contactMedium=").append(contactMedium).append("]");
		return builder.toString();
	}

}
