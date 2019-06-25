package com.myxh.coolshopping.entity;

import java.math.BigDecimal;
import java.util.Date;

public class BoxCheckInfo {

    /** id */
    private Long id;
    /** 入库任务单号 */
    private String checkNo;
    /** 状态 */
    private Integer status;
    /** 商品编码 */
    private String boxTypeCode;
    /** 商品名称 */
    private String boxTypeName;


    /** 客户商品编码 */
    private String customerCode;
    /** 客户商品名称 */
    private String customerName;

    /** 门店编码 */
    private String storedCode;
    /** 门店编码 */
    private String storedName;

    /** 计价单位（斤、两） */
    private String goodsUnit;

    private BigDecimal checkNum;
    /** 实际数量 */
    private BigDecimal realityNum;


    /** 尺寸 */
    private String size;



    /**包装严实 */
    private String color;



    /** 创建人 */
    private String createUser;

    /** 更新人 */
    private String updateUser;

    /** 司机编码 */
    private String driverCode;

    /** 司机名称 */
    private String driverName;

    /** 是否有效 */
    private Integer yn;

    /** 仓库编码 */
    private String warehouseCode;
    /** 仓库名称 */
    private String warehouseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public BigDecimal getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(BigDecimal checkNum) {
        this.checkNum = checkNum;
    }

    public BigDecimal getRealityNum() {
        return realityNum;
    }

    public void setRealityNum(BigDecimal realityNum) {
        this.realityNum = realityNum;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
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
