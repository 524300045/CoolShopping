package com.myxh.tms.response;

import com.myxh.tms.entity.BoxCheckInfo;

import java.util.List;

public class BoxCheckInfoRecyleResponse extends  TopResponse {

    private List<BoxCheckInfo> result;

    public List<BoxCheckInfo> getResult() {
        return result;
    }

    public void setResult(List<BoxCheckInfo> result) {
        this.result = result;
    }
}
