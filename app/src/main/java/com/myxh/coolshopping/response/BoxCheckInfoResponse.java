package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.BoxCheckInfo;

public class BoxCheckInfoResponse extends  TopResponse  {

    private BoxCheckInfo result;

    public BoxCheckInfo getResult() {
        return result;
    }

    public void setResult(BoxCheckInfo result) {
        this.result = result;
    }
}
