package com.myxh.coolshopping.request;

public class BoxOutDetailQueryRequest {
    /** 商品编码 */
    private String boxTypeCode;
    /** 商品名称 */
    private String boxTypeName;
    /** 客户商品编码 */
    private String customerCode;
    /** 客户商品名称 */
    private String customerName;

    /** 线路编码 */
    private String lineCode;
    /** 线路名称 */
    private String lineName;

    /** 门店编码 */
    private String storedCode;

    /** 每页大小 */
    private int pageSize;

    /** 当前页 */
    private int pageIndex;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
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

    public String getStoredCode() {
        return storedCode;
    }

    public void setStoredCode(String storedCode) {
        this.storedCode = storedCode;
    }
}
