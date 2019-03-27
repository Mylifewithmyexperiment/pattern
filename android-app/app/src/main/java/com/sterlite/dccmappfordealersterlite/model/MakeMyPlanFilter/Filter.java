package com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter;

/**
 * Created by etech-10 on 6/9/18.
 */

public class Filter {

    String title;
    String noOfResults;
    boolean isChecked;

    public Filter(String title, String noOfResults, boolean isChecked) {
        this.title = title;
        this.noOfResults = noOfResults;
        this.isChecked = isChecked;
    }

    public Filter() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoOfResults() {
        return noOfResults;
    }

    public void setNoOfResults(String noOfResults) {
        this.noOfResults = noOfResults;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
