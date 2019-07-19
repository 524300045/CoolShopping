package com.myxh.tms.response;

import com.myxh.tms.entity.LineStore;

import java.util.List;

public class LineStoreResponse extends  TopResponse {

    private List<LineStore> result;

    public List<LineStore> getResult() {
        return result;
    }

    public void setResult(List<LineStore> result) {
        this.result = result;
    }
}
