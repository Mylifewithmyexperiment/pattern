package com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel;

import java.io.Serializable;
import java.util.ArrayList;

public class MMPBase implements Serializable {

    private int itemImageId;
    private String itemTitle;
    private String addOnCategory;
    private boolean isSet = false;
    private int currentValuePosition = -1;
    private float tempProgress = 0;
    private MakeMyPlanItems lastItem;
    private ArrayList<MakeMyPlanItems> arrPlan;

    private boolean isSubItem = false;
    private boolean isSetSubVisible = false;
    private boolean isParentAtLast = false;


    public boolean isParentAtLast() {
        return isParentAtLast;
    }

    public void setParentAtLast(boolean parentAtLast) {
        isParentAtLast = parentAtLast;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getAddOnCategory() {
        return addOnCategory;
    }

    public void setAddOnCategory(String addOnCategory) {
        this.addOnCategory = addOnCategory;
    }

    public float getTempProgress() {
        return tempProgress;
    }

    public void setTempProgress(float tempProgress) {
        this.tempProgress = tempProgress;
    }

    public MakeMyPlanItems getLastItem() {
        return lastItem;
    }

    public void setLastItem(MakeMyPlanItems lastItem) {
        this.lastItem = lastItem;
    }

    public int getItemImageId() {
        return itemImageId;
    }

    public void setItemImageId(int itemImageId) {
        this.itemImageId = itemImageId;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public boolean isSetSubVisible() {
        return isSetSubVisible;
    }

    public void setSetSubVisible(boolean setSubVisible) {
        isSetSubVisible = setSubVisible;
    }

    public boolean isSubItem() {
        return isSubItem;
    }

    public void setSubItem(boolean subItem) {
        isSubItem = subItem;
    }

    public int getCurrentValuePosition() {
        return currentValuePosition;
    }

    public void setCurrentValuePosition(int currentValuePosition) {
        this.currentValuePosition = currentValuePosition;
    }

    public ArrayList<MakeMyPlanItems> getArrPlan() {
        return arrPlan;
    }

    public void setArrPlan(ArrayList<MakeMyPlanItems> arrPlan) {
        this.arrPlan = arrPlan;
    }


    @Override
    public String toString() {
        return "MMPBase{" +
                "itemImageId=" + itemImageId +
                ", itemTitle='" + itemTitle + '\'' +
                ", isSet=" + isSet +
                ", currentValuePosition=" + currentValuePosition +
                ", tempProgress=" + tempProgress +
                ", lastItem=" + lastItem +
                ", arrPlan=" + arrPlan +
                '}';
    }
}
