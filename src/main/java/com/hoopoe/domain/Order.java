package com.hoopoe.domain;

import com.hoopoe.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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

    @OneToOne
    @JoinColumn(name= "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime orderTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private  Double totalPrice;

    @ManyToMany
    @JoinTable(name = "t_order_product", joinColumns = @JoinColumn(name ="order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> roles = new HashSet<>();

}
