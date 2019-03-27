package com.sterlite.dccmappfordealersterlite.model.bssrest;

public enum AccountStatus {
	
	REGISTERED("ACS05","Registered"), ACTIVE("ACS01", "Active"), DEACTIVATE("ACS02", "Deactivated"), SUSPENDTEMPORARY("ACS06", "Suspend Temporary"), SUSPENDNONPAY("ACS08", "Suspend Non-Pay"), SUSPENDPERMANENT("ACS07", "Suspend Permanent"), TERMINATE("ACS04", "Terminated");
	
	private final String id;
	private final String alias;
	
	AccountStatus(String id, String alias) 
	{
	    this.id = id;
	    this.alias = alias;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return alias;
	}
	
	public static AccountStatus getNameById(String id) 
	{
		if(id!=null)
		{
			for (int i = 0; i < AccountStatus.values().length; i++) {
	            if (id.equals(AccountStatus.values()[i].id)){
	            	return AccountStatus.values()[i];
	            }
	        }
		}
        
		return null;
	}
	
	public static AccountStatus getNameByName(String alias) 
	{
		if(alias!=null)
		{
			for (int i = 0; i < AccountStatus.values().length; i++) {
	            if (alias.equals(AccountStatus.values()[i].alias)){
	            	return AccountStatus.values()[i];
	            }
	        }
		}
        
		return null;
	}
}
