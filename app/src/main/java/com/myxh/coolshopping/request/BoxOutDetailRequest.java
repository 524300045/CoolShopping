package com.myxh.coolshopping.request;

import java.math.BigDecimal;

public class BoxOutDetailRequest {

    private String boxTypeCode;

    /** 名称 */
    private String boxTypeName;


    /** 客户商品编码 */
    private String customerCode;
    /** 客户商品名称 */
    private String customerName;

    /** 门店编码 */
    private String storedCode;
    /** 门店编码 */
    private String storedName;

    /** 尺寸 */
    private String size;

    /** 实际数量 */
    private BigDecimal realityNum;

    /**包装严实 */
    private String color;

    private String remark;

    private String unit;

    /** 创建名称 */
    private String createUser;

    /** 修改名称 */
    private String updateUser;

    /** 有效标示（1：有效；0：无效） */
    private Integer yn;


    /** 线路编码 */
    private String lineCode;
    /** 线路名称 */
    private String lineName;

    /** 仓库编码 */
    private String warehouseCode;
    /** 仓库名称 */
    private String warehouseName;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getRealityNum() {
        return realityNum;
    }

    public void setRealityNum(BigDecimal realityNum) {
        this.realityNum = realityNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
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
}
