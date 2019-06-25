package com.myxh.coolshopping.entity;

public class Line {
    /** 主键id */
    private Long id;
    /** 线路编码 */
    private String lineCode;
    /** 线路名称 */
    private String lineName;
    /** 线路描述 */
    private String lineDescribe;
    /** 仓库编码 */
    private String warehouseCode;
    /** 仓库名称 */
    private String warehouseName;
    /** 状态 */
    private Integer status;

    /** 波次编码 */
    private String waveCode;

    /** 波次名称 */
    private String waveName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLineDescribe() {
        return lineDescribe;
    }

    public void setLineDescribe(String lineDescribe) {
        this.lineDescribe = lineDescribe;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWaveCode() {
        return waveCode;
    }

    public void setWaveCode(String waveCode) {
        this.waveCode = waveCode;
    }

    public String getWaveName() {
        return waveName;
    }

    public void setWaveName(String waveName) {
        this.waveName = waveName;
    }
}
