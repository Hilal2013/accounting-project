package com.cydeo.enums;

public enum Currency {
    EUR("Eur"), USD("Usd");

    private String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
