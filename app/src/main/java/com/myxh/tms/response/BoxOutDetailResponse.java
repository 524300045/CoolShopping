package com.myxh.tms.response;

import com.myxh.tms.entity.BoxOutDetail;

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
