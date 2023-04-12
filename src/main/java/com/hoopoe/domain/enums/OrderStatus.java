package com.hoopoe.domain.enums;

public enum OrderStatus {

    CREATED("Created"),
    CANCELED("Canceled"),
    DONE("Done");

    private String name;

    private OrderStatus(String name){
        this.name= name;
    }

    public String getName(){
        return name;
    }
}
