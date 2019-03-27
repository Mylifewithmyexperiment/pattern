package com.elitecore.elitesmp.pojo;

import java.io.Serializable;

/**
 * Created by salmankhan.yusufjai on 9/11/2015.
 */
public class CreditCardDetail implements Serializable {
    private String creditCardNumber;
    private String cvvNumber;

    @Override
    public String toString() {
        return "CreditCardDetail{" +
                "creditCardNumber='" + creditCardNumber + '\'' +
                ", cvvNumber='" + cvvNumber + '\'' +
                ", expMonth=" + expMonth +
                ", expYear=" + expYear +
                ", pg_selectedPayGw='" + pg_selectedPayGw + '\'' +
                '}';
    }

    private String expMonth;
    private String expYear;
    private String pg_selectedPayGw;

    public String getPg_AuthStatu() {
        return pg_AuthStatu;
    }

    public void setPg_AuthStatu(String pg_AuthStatu) {
        this.pg_AuthStatu = pg_AuthStatu;
    }

    private String pg_AuthStatu;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCardDetail)) return false;

        CreditCardDetail that = (CreditCardDetail) o;

        if (expMonth != null ? !expMonth.equals(that.expMonth) : that.expMonth != null)
            return false;
        if (expYear != null ? !expYear.equals(that.expYear) : that.expYear != null) return false;
        if (creditCardNumber != null ? !creditCardNumber.equals(that.creditCardNumber) : that.creditCardNumber != null)
            return false;
        if (cvvNumber != null ? !cvvNumber.equals(that.cvvNumber) : that.cvvNumber != null)
            return false;
        return !(pg_selectedPayGw != null ? !pg_selectedPayGw.equals(that.pg_selectedPayGw) : that.pg_selectedPayGw != null);

    }

    @Override
    public int hashCode() {
        int result = creditCardNumber != null ? creditCardNumber.hashCode() : 0;
        result = 31 * result + (cvvNumber != null ? cvvNumber.hashCode() : 0);
        result = 31 * result + (expMonth != null ? expMonth.hashCode() : 0);
        result = 31 * result + (expYear != null ? expYear.hashCode() : 0);
        result = 31 * result + (pg_selectedPayGw != null ? pg_selectedPayGw.hashCode() : 0);
        return result;
    }

    public String getCreditCardNumber() {

        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getPg_selectedPayGw() {
        return pg_selectedPayGw;
    }

    public void setPg_selectedPayGw(String pg_selectedPayGw) {
        this.pg_selectedPayGw = pg_selectedPayGw;
    }
}
