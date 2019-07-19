package com.myxh.tms.response;

import com.myxh.tms.entity.WareHouse;

import java.util.List;

public class WareResponse  extends TopResponse {
    private List<WareHouse> result;

    public List<WareHouse> getResult() {
        return result;
    }

    public void setResult(List<WareHouse> result) {
        this.result = result;
    }
}
