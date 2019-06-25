package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.BoxType;

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
