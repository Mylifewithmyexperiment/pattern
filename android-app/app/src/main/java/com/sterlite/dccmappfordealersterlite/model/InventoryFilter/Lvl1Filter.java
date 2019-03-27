package com.sterlite.dccmappfordealersterlite.model.InventoryFilter;

/**
 * Created by etech-10 on 7/9/18.
 */

public class Lvl1Filter {
    String text;
    boolean isChecked;
    Lvl2Filter lvl2Filter;

    public Lvl1Filter() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Lvl2Filter getLvl2Filter() {
        return lvl2Filter;
    }

    public void setLvl2Filter(Lvl2Filter lvl2Filter) {
        this.lvl2Filter = lvl2Filter;
    }
}
