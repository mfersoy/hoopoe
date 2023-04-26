package com.hoopoe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_order")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private String table;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @ManyToMany
    @JoinTable(name = "t_order_orderdetail", joinColumns = @JoinColumn(name ="order_id"),
            inverseJoinColumns = @JoinColumn(name = "orderdetail_id"))
    private Set<OrderDetail> orderDetailSet = new HashSet<>();
}
