package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.CustomerInfo;
import com.myxh.coolshopping.entity.Line;

import java.util.List;

public class CustomerInfoResponse extends  TopResponse {

    private List<CustomerInfo> result;

    public List<CustomerInfo> getResult() {
        return result;
    }

    public void setResult(List<CustomerInfo> result) {
        this.result = result;
    }
}
