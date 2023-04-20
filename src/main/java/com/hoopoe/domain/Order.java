package com.hoopoe.domain;

import com.hoopoe.domain.enums.OrderStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date orderTime;

    private Double productCost;

    private Double subTotal;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
