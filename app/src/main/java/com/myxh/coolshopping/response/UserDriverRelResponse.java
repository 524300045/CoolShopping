package com.myxh.coolshopping.response;

import com.myxh.coolshopping.entity.UserDriverRel;

public class UserDriverRelResponse extends  TopResponse {

    private UserDriverRel result;

    public UserDriverRel getResult() {
        return result;
    }

    public void setResult(UserDriverRel result) {
        this.result = result;
    }
}
