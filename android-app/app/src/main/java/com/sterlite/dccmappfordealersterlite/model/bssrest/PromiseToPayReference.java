package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;


public class PromiseToPayReference extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private Date dueDate;
	private String raisedBy;
	private List<InvoiceReference> invoiceRef;
	private String status;
	private Long ptpRequestId;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getRaisedBy() {
		return raisedBy;
	}
	public void setRaisedBy(String raisedBy) {
		this.raisedBy = raisedBy;
	}
	public List<InvoiceReference> getInvoiceRef() {
		return invoiceRef;
	}
	public void setInvoiceRef(List<InvoiceReference> invoiceRef) {
		this.invoiceRef = invoiceRef;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getPtpRequestId() {
		return ptpRequestId;
	}
	public void setPtpRequestId(Long ptpRequestId) {
		this.ptpRequestId = ptpRequestId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(80);
		builder.append("PromiseToPayReference [action=").append(action).append(", dueDate=").append(dueDate)
				.append(", raisedBy=").append(raisedBy).append(", invoiceRef=").append(invoiceRef).append(", status=")
				.append(status).append(", ptpRequestId=").append(ptpRequestId).append("]");
		return builder.toString();
	}

}
