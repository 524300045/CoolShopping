package com.myxh.coolshopping.entity;

import android.support.annotation.StringRes;

/**
 * Created by asus on 2016/8/31.
 */
public class HomeGridInfo {

    private @StringRes int gridIcon;
    private String gridTitle;

    private  String code;


    public HomeGridInfo(int gridIcon, String gridTitle) {
        this.gridIcon = gridIcon;
        this.gridTitle = gridTitle;
    }

    public HomeGridInfo(int gridIcon, String gridTitle,String code) {
        this.gridIcon = gridIcon;
        this.gridTitle = gridTitle;
        this.code=code;
    }

    public int getGridIcon() {
        return gridIcon;
    }

    public void setGridIcon(int gridIcon) {
        this.gridIcon = gridIcon;
    }

    public String getGridTitle() {
        return gridTitle;
    }

    public void setGridTitle(String gridTitle) {
        this.gridTitle = gridTitle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

