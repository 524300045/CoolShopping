package com.myxh.tms.response;


import com.myxh.tms.entity.User;

public class UserResponse extends TopResponse {

    private com.myxh.tms.entity.User result;

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }
}
