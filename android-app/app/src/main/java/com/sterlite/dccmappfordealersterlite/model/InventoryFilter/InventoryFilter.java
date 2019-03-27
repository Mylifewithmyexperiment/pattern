package com.sterlite.dccmappfordealersterlite.model.InventoryFilter;

import java.util.List;

/**
 * Created by etech-10 on 7/9/18.
 */

public class InventoryFilter {
    String luckyNumber;
    List<String> numberSeries;
    /*String lvl1Title;
    List<Lvl1Filter> lvl1Filters;
    */ String selectedNumSeries;
    boolean isAllChecked = true, isSortByHTL = true;

    public boolean isAllChecked() {
        return isAllChecked;
    }

    public void setAllChecked(boolean allChecked) {
        isAllChecked = allChecked;
    }

    public boolean isSortByHTL() {
        return isSortByHTL;
    }

    public void setSortByHTL(boolean sortByHTL) {
        isSortByHTL = sortByHTL;
    }

    public String getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public List<String> getNumberSeries() {
        return numberSeries;
    }

    public void setNumberSeries(List<String> numberSeries) {
        this.numberSeries = numberSeries;
    }

    /*public String getLvl1Title() {
        return lvl1Title;
    }

    public void setLvl1Title(String lvl1Title) {
        this.lvl1Title = lvl1Title;
    }

    public List<Lvl1Filter> getLvl1Filters() {
        return lvl1Filters;
    }

    public void setLvl1Filters(List<Lvl1Filter> lvl1Filters) {
        this.lvl1Filters = lvl1Filters;
    }
*/
    public String getSelectedNumSeries() {
        return selectedNumSeries;
    }

    public void setSelectedNumSeries(String selectedNumSeries) {
        this.selectedNumSeries = selectedNumSeries;
    }
}
