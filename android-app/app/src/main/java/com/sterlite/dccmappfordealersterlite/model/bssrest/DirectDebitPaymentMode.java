package com.sterlite.dccmappfordealersterlite.model.bssrest;

public enum DirectDebitPaymentMode {
	BANKACCOUNT("Bank Account"), DEBITCREDITCARD("Debit/Credit Card");
	private final String value;

	DirectDebitPaymentMode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static DirectDebitPaymentMode getNameByValue(String value)
	{
		if(value!=null)
		{
			for (int i = 0; i < DirectDebitPaymentMode.values().length; i++) {
	            if (value.equals(DirectDebitPaymentMode.values()[i].value))
	                return DirectDebitPaymentMode.values()[i];
	        }
		}
        
		return null;
	}
}
