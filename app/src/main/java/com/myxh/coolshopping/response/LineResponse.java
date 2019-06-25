package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.Line;

import java.util.List;

public class LineResponse extends  TopResponse{

    private List<Line> result;

    public List<Line> getResult() {
        return result;
    }

    public void setResult(List<Line> result) {
        this.result = result;
    }
}
