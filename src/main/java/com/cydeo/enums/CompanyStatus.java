package com.cydeo.enums;

public enum CompanyStatus {
    ACTIVE("Active"),PASSIVE("Passive");
    private String value;

    public String getValue() {
        return value;
    }

    CompanyStatus(String value) {
        this.value = value;
    }
}
