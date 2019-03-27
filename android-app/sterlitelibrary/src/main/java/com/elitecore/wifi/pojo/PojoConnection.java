package com.elitecore.wifi.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PojoConnection implements Serializable {

	private String ssid;
	private String security = null;
	private String encryptionMethod;
	private String authenticationMethod = null;
	private String phase2Authentication = null;
	private String identity = null;
	private String password = null;
	private String sim_operator_name;
	private boolean showPassword = false;
	private boolean isActive;
	private boolean isDefault;
	private boolean isOutOfRange;
	private boolean isWisprEnabled;
	private String wisprAuthenticationMethod;
	private String wisprUsarname;
	private String wisprPassword;
	private boolean checkOperator=false;
	private int simSlot=1;

	public boolean isLocal() {
		return isLocal;
	}

	public void setLocal(boolean local) {
		isLocal = local;
	}

	private boolean isLocal;

	public int getSimSlot() {
		return simSlot;
	}

	public void setSimSlot(int simSlot) {
		this.simSlot = simSlot;
	}



	public boolean isCheckOperator() {
		return checkOperator;
	}

	public void setCheckOperator(boolean checkOperator) {
		this.checkOperator = checkOperator;
	}



	public PojoConnection() {
		super();
	}

	
	public PojoConnection(String ssid) {
		this.ssid = ssid;
	}
	public String getSim_operator_name() {
		return sim_operator_name;
	}

	public void setSim_operator_name(String sim_operator_name) {
		this.sim_operator_name = sim_operator_name;
	}
	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}


	public String getAuthenticationMethod() {
		return authenticationMethod;
	}

	public void setAuthenticationMethod(String authenticationMethod) {
		this.authenticationMethod = authenticationMethod;
	}

	public String getPhase2Authentication() {
		return phase2Authentication;
	}

	public void setPhase2Authentication(String phase2Authentication) {
		this.phase2Authentication = phase2Authentication;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isShowPassword() {
		return showPassword;
	}

	public void setShowPassword(boolean showPassword) {
		this.showPassword = showPassword;
	}

	public String getSsid() {
		return ssid;
	}



	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public String getEncryptionMethod() {
		return encryptionMethod;
	}

	public void setEncryptionMethod(String encryptionMethod) {
		this.encryptionMethod = encryptionMethod;
	}

	public boolean isOutOfRange() {
		return isOutOfRange;
	}

	public boolean isWisprEnabled() {
		return isWisprEnabled;
	}

	public void setWisprEnabled(boolean isWisprEnabled) {
		this.isWisprEnabled = isWisprEnabled;
	}

	public String getWisprAuthenticationMethod() {
		return wisprAuthenticationMethod;
	}

	public void setWisprAuthenticationMethod(String wisprAuthenticationMethod) {
		this.wisprAuthenticationMethod = wisprAuthenticationMethod;
	}

	public String getWisprUsarname() {
		return wisprUsarname;
	}

	public void setWisprUsarname(String wisprUsarname) {
		this.wisprUsarname = wisprUsarname;
	}

	public String getWisprPassword() {
		return wisprPassword;
	}

	public void setWisprPassword(String wisprPassword) {
		this.wisprPassword = wisprPassword;
	}

	public void setOutOfRange(boolean isOutOfRange) {
		this.isOutOfRange = isOutOfRange;
	}

	@Override
	public String toString() {
		return "PojoConnection [ssid=" + ssid + ", security=" + security
				+ ", encryptionMethod=" + encryptionMethod
				+ ", authenticationMethod=" + authenticationMethod
				+ ", phase2Authentication=" + phase2Authentication
				+ ", identity=" + identity + ", password=" + password
				+ ", sim_operator_name=" + sim_operator_name
				+ ", showPassword=" + showPassword + ", isActive=" + isActive
				+ ", isDefault=" + isDefault + ", isOutOfRange=" + isOutOfRange
				+ ", isWisprEnabled=" + isWisprEnabled
				+ ", wisprAuthenticationMethod=" + wisprAuthenticationMethod
				+ ", wisprUsarname=" + wisprUsarname + ", wisprPassword="
				+ wisprPassword + "]";
	}
	

}
