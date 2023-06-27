package com.cydeo.enums;

public enum ClientVendorType {
    VENDOR("Vendor"),client("Client");
    private final String value;

    public String getValue() {
        return value;
    }
    ClientVendorType(String value) {
        this.value = value;
    }
}
