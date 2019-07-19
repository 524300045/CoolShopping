package com.myxh.coolshopping.request;

public class BoxTypeRequest {

    /** 客户商品编码 */
    private String customerCode;
    /** 客户商品名称 */
    private String customerName;


    /** 门店编码 */
    private String storedCode;
    /** 门店编码 */
    private String storedName;

    private String warehouseCode;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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
}
