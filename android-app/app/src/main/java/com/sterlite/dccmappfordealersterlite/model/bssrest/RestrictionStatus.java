package com.sterlite.dccmappfordealersterlite.model.bssrest;

public enum RestrictionStatus {
	Blacklist("B"), Whitelist("W"), Greylist("G");
	
	private final String id;
	
	RestrictionStatus(String id) {
	    this.id = id;
	}

	public String getId() {
		return id;
	}

	public static RestrictionStatus getNameById(String id) 
	{
		if(id!=null)
		{
			for (int i = 0; i < RestrictionStatus.values().length; i++) {
	            if (id.equals(RestrictionStatus.values()[i].id))
	                return RestrictionStatus.values()[i];
	        }
		}
        
		return null;
	}
}
