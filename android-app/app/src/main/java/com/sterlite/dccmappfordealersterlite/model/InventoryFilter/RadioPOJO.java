package com.sterlite.dccmappfordealersterlite.model.InventoryFilter;

/**
 * Created by etech-10 on 7/9/18.
 */

public class RadioPOJO {
    String text;
    boolean isChecked;

    public RadioPOJO() {
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
}
