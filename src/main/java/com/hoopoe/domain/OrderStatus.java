package com.hoopoe.domain;

import com.hoopoe.domain.enums.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "t_orderstatus")
@Entity
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private OrderStatusType type;

    @Override
    public String toString() {
        return "Order{" +
                "type=" + type +
                "}";
    }
}
