package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

/**
 * The Class InventoryDetail.
 */
public class InventoryDetail extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private String inventoryNumber;
	private InventoryDetail childInventoryDetail;
	private String networkServiceIdentifier;
	private String subGroupType;
	private String groupType;
	private String packageId;
	private String status;
	private String manualInventoryStatus;
	private String resourceInventoryStatus;
	private TimePeriod validFor;
	private Action action;
	private List<Characteristic> demographicParameters; // NOSONAR
	private List<Characteristic> additionalParameters; // NOSONAR
	private String vanityChargeThrough;

	public List<Characteristic> getAdditionalParameters() {
		return additionalParameters;
	}

	public void setAdditionalParameters(List<Characteristic> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}

	public String getVanityChargeThrough() {
		return vanityChargeThrough;
	}

	public void setVanityChargeThrough(String vanityChargeThrough) {
		this.vanityChargeThrough = vanityChargeThrough;
	}

	public String getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public InventoryDetail getChildInventoryDetail() {
		return childInventoryDetail;
	}

	public void setChildInventoryDetail(InventoryDetail childInventoryDetail) {
		this.childInventoryDetail = childInventoryDetail;
	}

	public String getNetworkServiceIdentifier() {
		return networkServiceIdentifier;
	}

	public void setNetworkServiceIdentifier(String networkServiceIdentifier) {
		this.networkServiceIdentifier = networkServiceIdentifier;
	}

	public String getSubGroupType() {
		return subGroupType;
	}

	public void setSubGroupType(String subGroupType) {
		this.subGroupType = subGroupType;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getManualInventoryStatus() {
		return manualInventoryStatus;
	}

	public void setManualInventoryStatus(String manualInventoryStatus) {
		this.manualInventoryStatus = manualInventoryStatus;
	}

	public String getResourceInventoryStatus() {
		return resourceInventoryStatus;
	}

	public void setResourceInventoryStatus(String resourceInventoryStatus) {
		this.resourceInventoryStatus = resourceInventoryStatus;
	}

	public List<Characteristic> getDemographicParameters() {
		return demographicParameters;
	}

	public void setDemographicParameters(List<Characteristic> demographicParameters) {
		this.demographicParameters = demographicParameters;
	}

	public TimePeriod getValidFor() {
		return validFor;
	}

	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(800);
		builder.append("InventoryDetail [inventoryNumber=");
		builder.append(inventoryNumber);
		builder.append(", childInventoryDetail=");
		builder.append(childInventoryDetail);
		builder.append(", networkServiceIdentifier=");
		builder.append(networkServiceIdentifier);
		builder.append(", subGroupType=");
		builder.append(subGroupType);
		builder.append(", groupType=");
		builder.append(groupType);
		builder.append(", packageId=");
		builder.append(packageId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", manualInventoryStatus=");
		builder.append(manualInventoryStatus);
		builder.append(", resourceInventoryStatus=");
		builder.append(resourceInventoryStatus);
		builder.append(", validFor=");
		builder.append(validFor);
		builder.append(", action=");
		builder.append(action);
		builder.append(", demographicParameters=");
		builder.append(demographicParameters);
		builder.append(", additionalParameters=");
		builder.append(additionalParameters);
		builder.append(", vanityChargeThrough=");
		builder.append(vanityChargeThrough);
		builder.append("]");
		return builder.toString();
	}
}