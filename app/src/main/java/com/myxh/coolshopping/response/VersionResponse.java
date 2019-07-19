package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.VersionInfo;

public class VersionResponse extends  TopResponse {

    private VersionInfo result;

    public VersionInfo getResult() {
        return result;
    }

    public void setResult(VersionInfo result) {
        this.result = result;
    }
}
