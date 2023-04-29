package com.hoopoe.domain.enums;

public enum OrderStatusType {

    ORDER_CREATED("Created"),
    ORDER_CANCEL("Cansel"),
    ROLE_DONE("Done");

    private String name;

    private OrderStatusType(String name){
        this.name= name;
    }

    public String getName(){
        return name;
    }

    //I will add to order but ı didn't add yet
}
