package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.StoreCheckInfo;
import com.myxh.coolshopping.entity.StoreInfo;

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
