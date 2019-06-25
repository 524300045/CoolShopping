package com.myxh.coolshopping.request;

import java.math.BigDecimal;

public class BoxCheckInfoAddRequest {
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


    private BigDecimal checkNum;

    /** 仓库编码 */
    private String warehouseCode;
    /** 仓库名称 */
    private String warehouseName;

    private String userName;

    private String driverCode;

    private String driverName;

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

    public BigDecimal getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(BigDecimal checkNum) {
        this.checkNum = checkNum;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
