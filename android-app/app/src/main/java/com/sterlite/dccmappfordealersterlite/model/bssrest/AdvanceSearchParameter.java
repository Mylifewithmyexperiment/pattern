package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class AdvanceSearchParameter implements Serializable{
	private String searchBy;
	private String value;
	private String valueComparison;
	public String getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueComparison() {
		return valueComparison;
	}
	public void setValueComparison(String valueComparison) {
		this.valueComparison = valueComparison;
	}
	@Override
	public String toString() {
		return "AdvanceSearchParameter [searchBy=" + searchBy + ", value=" + value + ", valueComparison="
				+ valueComparison + "]";
	}
	
}
