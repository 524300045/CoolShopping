package com.myxh.coolshopping.response;


import com.myxh.coolshopping.entity.User;

public class UserResponse extends TopResponse {

    private com.myxh.coolshopping.entity.User result;

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }
}
