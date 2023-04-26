package com.hoopoe.repository;

import com.hoopoe.domain.CartItem;
import com.hoopoe.domain.OrderDetail;
import com.hoopoe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    List<OrderDetail> findByUser(User user);
}
