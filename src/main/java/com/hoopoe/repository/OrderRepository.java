package com.hoopoe.repository;

import com.hoopoe.domain.Product;
import com.hoopoe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderRepository,Long> {

    boolean existsByProduct(Product product);

    boolean existsByUser(User user);


}
