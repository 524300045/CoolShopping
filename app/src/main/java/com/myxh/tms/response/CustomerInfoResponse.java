package com.myxh.tms.response;

import com.myxh.tms.entity.CustomerInfo;

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
