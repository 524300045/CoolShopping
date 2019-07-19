package com.myxh.tms.response;

import com.myxh.tms.entity.StoreCheckInfo;

import java.util.List;

public class StoreCheckResponse extends TopResponse  {

    private List<StoreCheckInfo>  result;

    public List<StoreCheckInfo> getResult() {
        return result;
    }

    public void setResult(List<StoreCheckInfo> result) {
        this.result = result;
    }
}
