package com.sterlite.dccmappfordealersterlite.model;

public class LandingPageItem {
    private int menuId;
    private int menuResId;
    private String menuName;
    private int menuNameColor;

    private int menuCirleColor;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getMenuResId() {
        return menuResId;
    }

    public void setMenuResId(int menuResId) {
        this.menuResId = menuResId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuNameColor() {
        return menuNameColor;
    }

    public void setMenuNameColor(int menuNameColor) {
        this.menuNameColor = menuNameColor;
    }

    public int getMenuCirleColor() {
        return menuCirleColor;
    }

    public void setMenuCirleColor(int menuCirleColor) {
        this.menuCirleColor = menuCirleColor;
    }
}
