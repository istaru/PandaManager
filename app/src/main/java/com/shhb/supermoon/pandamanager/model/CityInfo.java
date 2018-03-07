package com.shhb.supermoon.pandamanager.model;

/**
 * Created by superMoon on 2017/7/31.
 */
public class CityInfo {
    private String name;
    private String pinyin;

    public CityInfo() {}

    public CityInfo(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
