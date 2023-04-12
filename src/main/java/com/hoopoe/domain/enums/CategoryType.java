package com.hoopoe.domain.enums;

public enum CategoryType {
    CATEGORY_DRINK("Drink"),

    CATEGORY_FOOD("Food"),

    CATEGORY_OTHER("Other");

    private String name;

    private CategoryType(String name){
        this.name= name;
    }

    public String getName(){
        return name;
    }
}
