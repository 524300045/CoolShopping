package com.myxh.coolshopping.request;

import java.math.BigDecimal;

public class BoxCheckInfoRecyleUpdateRequest {

    private BigDecimal realityNum;

    private String checkNo;

    private String userName;

    public BigDecimal getRealityNum() {
        return realityNum;
    }

    public void setRealityNum(BigDecimal realityNum) {
        this.realityNum = realityNum;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
