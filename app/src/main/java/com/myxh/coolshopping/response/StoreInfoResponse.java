package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.Line;
import com.myxh.coolshopping.entity.StoreInfo;

import java.util.List;

public class StoreInfoResponse extends TopResponse {

    private List<StoreInfo> result;

    public List<StoreInfo> getResult() {
        return result;
    }

    public void setResult(List<StoreInfo> result) {
        this.result = result;
    }
}
