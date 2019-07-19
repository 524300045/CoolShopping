package com.myxh.tms.response;

import com.myxh.tms.entity.VersionInfo;

public class VersionResponse extends  TopResponse {

    private VersionInfo result;

    public VersionInfo getResult() {
        return result;
    }

    public void setResult(VersionInfo result) {
        this.result = result;
    }
}
