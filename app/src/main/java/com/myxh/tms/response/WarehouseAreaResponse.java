package com.myxh.tms.response;

import com.myxh.tms.entity.WarehouseArea;

import java.util.List;

public class WarehouseAreaResponse extends TopResponse {

    private List<WarehouseArea> result;

    public List<WarehouseArea> getResult() {
        return result;
    }

    public void setResult(List<WarehouseArea> result) {
        this.result = result;
    }
}
