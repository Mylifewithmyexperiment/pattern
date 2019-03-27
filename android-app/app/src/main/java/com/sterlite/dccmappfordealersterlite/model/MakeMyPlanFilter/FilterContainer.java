package com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter;

import java.util.ArrayList;

/**
 * Created by etech-10 on 6/9/18.
 */

public class FilterContainer  {
    String title;
    ArrayList<Filter> filterList;

    public FilterContainer(String title, ArrayList<Filter> filterList) {
        this.title = title;
        this.filterList = filterList;
    }

    public FilterContainer() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(ArrayList<Filter> filterList) {
        this.filterList = filterList;
    }
}
