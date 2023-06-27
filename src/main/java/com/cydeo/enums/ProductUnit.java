package com.cydeo.enums;

public enum ProductUnit {
//
//    LBS - Value will be "Libre"
//    GALLON - Value will be "Gallon"
//    PCS - Value will be "Pieces"
//    KG - Value will be "Kilogram"
//    METER - Value will be "Meter"
//    INCH - Value will be "Inch"
//    FEET - Value will be "Feet"

    LBS("Libre"), GALLON("Gallon"), PCS("Pieces"), KG("Kilogram"), METER("Meter"),
    INCH("Inch"), FEET("Feet");
    ProductUnit(String value) {
        this.value = value;
    }
    private String value;
}
