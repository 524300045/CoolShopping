package com.myxh.tms.response;

import com.myxh.tms.entity.UserDriverRel;

public class UserDriverRelResponse extends  TopResponse {

    private UserDriverRel result;

    public UserDriverRel getResult() {
        return result;
    }

    public void setResult(UserDriverRel result) {
        this.result = result;
    }
}
