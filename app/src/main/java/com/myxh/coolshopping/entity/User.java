package com.myxh.coolshopping.entity;

import java.util.List;
import java.util.Map;

public class User {

    private long id ;

    private String name ;

    private String cnName ;

    private String email ;

    private String companyName ;

    private String companyCode ;

    private List<Menu> menuDtos ;

    private String defaultCustomerCode;

    private Map<String,String> customerMap;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public List<Menu> getMenuDtos() {
        return menuDtos;
    }

    public void setMenuDtos(List<Menu> menuDtos) {
        this.menuDtos = menuDtos;
    }

    public String getDefaultCustomerCode() {
        return defaultCustomerCode;
    }

    public void setDefaultCustomerCode(String defaultCustomerCode) {
        this.defaultCustomerCode = defaultCustomerCode;
    }

    public Map<String, String> getCustomerMap() {
        return customerMap;
    }

    public void setCustomerMap(Map<String, String> customerMap) {
        this.customerMap = customerMap;
    }
}
