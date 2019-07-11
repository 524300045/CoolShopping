package com.myxh.coolshopping.request;

public class CustomerInfoRequest {
    /** 客户编码 */
    private String customerCode;
    /** 客户名称 */
    private String customerName;

    /** 仓库编码 */
    private String warehouseCode;

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

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
}
