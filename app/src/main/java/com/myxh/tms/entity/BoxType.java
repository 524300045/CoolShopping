package com.myxh.tms.entity;

import java.math.BigDecimal;

public class BoxType {
    /** 主键id */
    private Long id;
    /** 编码 */
    private String boxTypeCode;

    /** 名称 */
    private String boxTypeName;
    /** 长（M） */
    private BigDecimal boxLength;
    /** 宽（M） */
    private BigDecimal boxWidth;
    /** 高（M） */
    private BigDecimal boxHigh;
    /** 尺寸 */
    private String size;

    private BigDecimal checkNum;

    /**包装严实 */
    private String color;

    private String remark;

    private String unit;

    public BigDecimal remainNum;

    public BigDecimal getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(BigDecimal remainNum) {
        this.remainNum = remainNum;
    }

    public BigDecimal getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(BigDecimal checkNum) {
        this.checkNum = checkNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getBoxLength() {
        return boxLength;
    }

    public void setBoxLength(BigDecimal boxLength) {
        this.boxLength = boxLength;
    }

    public BigDecimal getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(BigDecimal boxWidth) {
        this.boxWidth = boxWidth;
    }

    public BigDecimal getBoxHigh() {
        return boxHigh;
    }

    public void setBoxHigh(BigDecimal boxHigh) {
        this.boxHigh = boxHigh;
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
}
