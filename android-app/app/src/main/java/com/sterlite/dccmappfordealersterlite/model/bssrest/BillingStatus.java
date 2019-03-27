package com.sterlite.dccmappfordealersterlite.model.bssrest;

public enum BillingStatus {

	ENABLE("ACBS01", "Enabled"), DISABLE("ACBS03", "Disabled"), ONHOLD("","");
	private final String id;
	private final String alias;

	BillingStatus(String v, String alias) {
	    this.id = v;
	    this.alias = alias;
	}

	public String getName() {
		return alias;
	}
	
	public String getId() {
		return id;
	}
	
	public static BillingStatus getNameById(String id) 
	{
		if(id!=null)
		{
			for (int i = 0; i < BillingStatus.values().length; i++) {
	            if (id.equals(BillingStatus.values()[i].id))
	                return BillingStatus.values()[i];
	        }
		}
        
		return null;
	}
	
	public static BillingStatus getNameByName(String alias) 
	{
		if(alias!=null)
		{
	        for (int i = 0; i < BillingStatus.values().length; i++) {
	            if (alias.equals(BillingStatus.values()[i].alias))
	                return BillingStatus.values()[i];
	        }
		}
		return null;
	}
}
