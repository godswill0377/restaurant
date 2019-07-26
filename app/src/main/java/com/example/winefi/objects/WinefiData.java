package com.example.winefi.objects;

public class WinefiData {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String name;
    public String icon;

    @Override
    public String toString() {
        return "WinefiData{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", alis='" + alis + '\'' +
                '}';
    }

    public String getAlis() {
        return alis;
    }

    public void setAlis(String alis) {
        this.alis = alis;
    }

    public String alis;

}
