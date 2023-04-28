package com.hoopoe.dto;

import com.hoopoe.domain.OrderDetail;
import com.hoopoe.domain.User;
import com.hoopoe.mapper.OrderMapper;
import com.hoopoe.repository.OrderDetailRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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

    private Map<String,Integer> orderDetailSet;

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet){

        Map<String,Integer> orderDetail = new HashMap<>();

        orderDetailSet.forEach(orderDetail1 -> orderDetail.put(orderDetail1.getProduct().getName(),orderDetail1.getQuantity()));

        this.orderDetailSet=orderDetail;
    }


}
