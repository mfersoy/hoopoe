package com.hoopoe.dto;

import com.hoopoe.domain.OrderDetail;
import com.hoopoe.domain.enums.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Double totalPrice;

    private String tables;

    private LocalDateTime localDateTime;

    private String user;

    private OrderStatusType orderStatusType;

    private Map<String,Integer> orderDetailSet;

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet){

        Map<String,Integer> orderDetail = new HashMap<>();

        orderDetailSet.forEach(orderDetail1 -> orderDetail.put(orderDetail1.getProduct().getName(),orderDetail1.getQuantity()));

        this.orderDetailSet=orderDetail;
    }


}
