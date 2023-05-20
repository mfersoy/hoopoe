package com.hoopoe.repository;

import com.hoopoe.domain.Order;
import com.hoopoe.domain.OrderStatus;
import com.hoopoe.domain.User;
import com.hoopoe.domain.enums.OrderStatusType;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Page<Order> findAllByUser(User user, Pageable pageable);

    Optional<Order> findByIdAndUser(Long id, User user);

    @Query("Select o from Order o JOIN o.orderStatuses os WHERE os.type=:status")
    List<Order> findByStatus(String status);


}
