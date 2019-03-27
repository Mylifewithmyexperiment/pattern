package com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MakeMyPlanLists {
    ArrayList<MakeMyPlanItems> arrBasicPlan;
    ArrayList<MakeMyPlanItems> arrDataAddOn;
    ArrayList<MakeMyPlanItems> arrDataSubAddOn;
    ArrayList<MakeMyPlanItems> arrMinAddOn;
    ArrayList<MakeMyPlanItems> arrSMSAddOn;
    ArrayList<MakeMyPlanItems> arrRoamingAddOn;

    public ArrayList<MakeMyPlanItems> getArrBasicPlan() {
        return arrBasicPlan;
    }

    public void setArrBasicPlan(ArrayList<MakeMyPlanItems> arrBasicPlan) {
        this.arrBasicPlan = arrBasicPlan;
    }

    public ArrayList<MakeMyPlanItems> getArrDataAddOn() {
        return arrDataAddOn;
    }

    public ArrayList<MakeMyPlanItems> getArrDataSubAddOn() {
        return arrDataSubAddOn;
    }

    public void setArrDataSubAddOn(ArrayList<MakeMyPlanItems> arrDataSubAddOn) {
        this.arrDataSubAddOn = arrDataSubAddOn;
    }

    public void setArrDataAddOn(ArrayList<MakeMyPlanItems> arrDataAddOn) {
        this.arrDataAddOn = sortArray(arrDataAddOn);
    }

    public ArrayList<MakeMyPlanItems> getArrMinAddOn() {
        return arrMinAddOn;
    }

    public void setArrMinAddOn(ArrayList<MakeMyPlanItems> arrMinAddOn) {
        this.arrMinAddOn = sortArray(arrMinAddOn);
    }

    public ArrayList<MakeMyPlanItems> getArrSMSAddOn() {
        return arrSMSAddOn;
    }

    public void setArrSMSAddOn(ArrayList<MakeMyPlanItems> arrSMSAddOn) {
        this.arrSMSAddOn = sortArray(arrSMSAddOn);
    }

    public ArrayList<MakeMyPlanItems> getArrRoamingAddOn() {
        return arrRoamingAddOn;
    }

    public void setArrRoamingAddOn(ArrayList<MakeMyPlanItems> arrRoamingAddOn) {
        this.arrRoamingAddOn = sortArray(arrRoamingAddOn);
    }

    public static ArrayList<MakeMyPlanItems> sortArray(ArrayList<MakeMyPlanItems> planItems) {
        Collections.sort(planItems, new Comparator<MakeMyPlanItems>() {
            @Override
            public int compare(MakeMyPlanItems o1, MakeMyPlanItems o2) {
                return o1.getOneTimeProductPrice().getValue() - o2.getOneTimeProductPrice().getValue();
            }
        });
        return planItems;
    }
}
