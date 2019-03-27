package com.sterlite.dccmappfordealersterlite.model;

public class Language {
    private String languageToDisplay;
    private boolean isSelected=false;
    private String languageCode;

    public Language(String languageToDisplay,String languageCode, boolean isSelected ) {
        this.languageToDisplay = languageToDisplay;
        this.isSelected = isSelected;
        this.languageCode = languageCode;
    }

    public String getLanguageToDisplay() {
        return languageToDisplay;
    }

    public void setLanguageToDisplay(String languageToDisplay) {
        this.languageToDisplay = languageToDisplay;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
