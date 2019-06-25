package com.myxh.coolshopping.request;

public class BoxCheckInfoRequest {
    /** 编码 */
    private String boxTypeCode;

    /** 名称 */
    private String boxTypeName;

    /** 门店编码 */
    private String storedCode;
    /** 门店名称 */
    private String storedName;

    /** 货主编码 */
    private String customerCode;

    /** 货主编码 */
    private String customerName;

    private Integer status;

    private String lineCode;

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getBoxTypeCode() {
        return boxTypeCode;
    }

    public void setBoxTypeCode(String boxTypeCode) {
        this.boxTypeCode = boxTypeCode;
    }

    public String getBoxTypeName() {
        return boxTypeName;
    }

    public void setBoxTypeName(String boxTypeName) {
        this.boxTypeName = boxTypeName;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
