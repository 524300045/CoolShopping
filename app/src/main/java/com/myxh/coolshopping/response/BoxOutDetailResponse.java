package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.BoxOutDetail;

import java.util.List;

public class BoxOutDetailResponse extends  TopResponse {

    private List<BoxOutDetail> result;

    public List<BoxOutDetail> getResult() {
        return result;
    }

    public void setResult(List<BoxOutDetail> result) {
        this.result = result;
    }
}
