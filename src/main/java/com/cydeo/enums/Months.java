package com.cydeo.enums;

public enum Months {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    private final String value;
    private Months next;

    static {
        JANUARY.next = FEBRUARY;
        FEBRUARY.next = MARCH;
        MARCH.next = APRIL;
        APRIL.next = MAY;
        MAY.next = JUNE;
        JUNE.next = JULY;
        JULY.next = AUGUST;
        AUGUST.next = SEPTEMBER;
        SEPTEMBER.next = OCTOBER;
        OCTOBER.next = NOVEMBER;
        NOVEMBER.next = DECEMBER;
        DECEMBER.next = JANUARY;
    }

    Months(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Months getNextMonth() {
        return next;
    }

}
