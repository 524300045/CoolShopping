package com.myxh.tms.entity;

public class StoreInfo {

    /** 门店编码 */
    private String storedCode;
    /** 门店名称 */
    private String storedName;

    /** 货主编码 */
    private String customerCode;
    /** 货主 */
    private String customerName;

    public String getStoredCode() {
        return storedCode;
    }

    public void setStoredCode(String storedCode) {
        this.storedCode = storedCode;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
