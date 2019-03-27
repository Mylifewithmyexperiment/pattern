package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;



public class AddonSubscriptionResponseDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ServiceInstanceDetail successSubscriptions;
	private List<ResponseMessage> failureSubscriptions; // NOSONAR
	
	public ServiceInstanceDetail getSuccessSubscriptions() {
		return successSubscriptions;
	}
	public void setSuccessSubscriptions(ServiceInstanceDetail successSubscriptions) {
		this.successSubscriptions = successSubscriptions;
	}
	public List<ResponseMessage> getFailureSubscriptions() {
		return failureSubscriptions;
	}
	public void setFailureSubscriptions(List<ResponseMessage> failureSubscriptions) {
		this.failureSubscriptions = failureSubscriptions;
	}
	@Override
	public String toString() {
		return "AddonSubscriptionResponseDetail [successSubscriptions=" + successSubscriptions
				+ ", failureSubscriptions=" + failureSubscriptions + "]";
	}

}
