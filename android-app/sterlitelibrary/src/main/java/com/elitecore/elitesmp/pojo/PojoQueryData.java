package com.elitecore.elitesmp.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by viratsinh.parmar on 8/31/2015.
 *
 * This class is used when Webin service like DBSCAN is invoked ,
 * this class generate getter/setter values for querydata which is passed in web service as key and values.
 */
public class PojoQueryData implements Serializable{


    public List<PojoKeyValueOpr> getData() {
        return data;
    }

    public void setData(List<PojoKeyValueOpr> data) {
        this.data = data;
    }

    public List<PojoKeyValueOpr> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<PojoKeyValueOpr> criteria) {
        this.criteria = criteria;
    }

    public List<PojoKeyValueOpr> getOrder() {
        return order;
    }

    public void setOrder(List<PojoKeyValueOpr> order) {
        this.order = order;
    }

    private List<PojoKeyValueOpr> data;
    private List<PojoKeyValueOpr>  criteria;
    private List<PojoKeyValueOpr>  order;


   public class PojoKeyValueOpr implements  Serializable
    {

      private String  key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getOpr() {
            return opr;
        }

        public void setOpr(String opr) {
            this.opr = opr;
        }

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }

        private String value;
        private String ordertype;
        private String opr;

    }
}
