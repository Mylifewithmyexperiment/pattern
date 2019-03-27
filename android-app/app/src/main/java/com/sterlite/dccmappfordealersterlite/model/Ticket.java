package com.sterlite.dccmappfordealersterlite.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket implements Serializable{

    @JsonProperty("ticketNumber")
    private String ticketNumber;

    @JsonProperty("title")
    private String title;

    @JsonProperty("category")
    private String category;

    @JsonProperty("sub_category")
    private String subCategory;

    @JsonProperty("description")
    private String description;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdTime")
    private String createdTime;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("customerNumber")
    private String customerNumber;

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Ticket{");
        sb.append("ticketNumber='").append(ticketNumber).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", subCategory='").append(subCategory).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", priority='").append(priority).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", createdTime='").append(createdTime).append('\'');
        sb.append(", responseCode='").append(responseCode).append('\'');
        sb.append(", responseMessage='").append(responseMessage).append('\'');
        sb.append(", customerNumber='").append(customerNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String printResponse() {
        final StringBuffer sb = new StringBuffer("Ticket{");
        sb.append("responseCode='").append(responseCode).append('\'');
        sb.append(", responseMessage='").append(responseMessage).append('\'');
        sb.append(", customerNumber='").append(customerNumber).append('\'');
        sb.append(", ticketNumber='").append(ticketNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
