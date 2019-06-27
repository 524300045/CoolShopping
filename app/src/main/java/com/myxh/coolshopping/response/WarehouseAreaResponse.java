package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.WarehouseArea;

import java.util.List;

import rx.observers.TestObserver;

public class WarehouseAreaResponse extends TopResponse {

    private List<WarehouseArea> result;

    public List<WarehouseArea> getResult() {
        return result;
    }

    public void setResult(List<WarehouseArea> result) {
        this.result = result;
    }
}
