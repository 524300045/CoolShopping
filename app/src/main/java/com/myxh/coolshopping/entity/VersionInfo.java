package com.myxh.coolshopping.entity;

public class VersionInfo {

    /** 娑撳鏁璱d */
    private Long id;
    /** 缁崵绮虹紓鏍垳 */
    private String systemCode;
    /**缁崵绮洪崥宥囆� */
    private String systemName;
    /** 閻楀牊婀伴崣锟�*/
    private String versionCode;

    private String url;
    /** 鏉烇箒绶犵�规枻绱橫閿涳拷 */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
