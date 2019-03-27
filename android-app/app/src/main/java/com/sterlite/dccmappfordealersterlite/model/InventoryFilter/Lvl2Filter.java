package com.sterlite.dccmappfordealersterlite.model.InventoryFilter;

import java.util.List;

/**
 * Created by etech-10 on 7/9/18.
 */

public class Lvl2Filter {
    String title;
    List<RadioPOJO> radioList;

    public Lvl2Filter() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RadioPOJO> getRadioList() {
        return radioList;
    }

    public void setRadioList(List<RadioPOJO> radioList) {
        this.radioList = radioList;
    }
}
