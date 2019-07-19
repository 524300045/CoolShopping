package com.myxh.tms.response;

import com.myxh.tms.entity.BoxType;

import java.util.List;

public class BoxTypeResponse extends  TopResponse {

    private List<BoxType> result;

    public List<BoxType> getResult() {
        return result;
    }

    public void setResult(List<BoxType> result) {
        this.result = result;
    }
}
