package com.hoopoe.repository;

import com.hoopoe.domain.CartItem;
import com.hoopoe.domain.Product;
import com.hoopoe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

        List<CartItem> findByUser(User user);

        CartItem findByUserAndProduct(User user, Product product);

        @Modifying
        @Query("UPDATE CartItem c SET c.quantity= ?1 WHERE c.user.id= ?2 AND c.product.id= ?3")
        public void updateQuantity(Integer quantity,Long userId, Long productId);

        @Modifying
        @Query("DELETE FROM CartItem c WHERE c.user.id= ?1 AND c.product.id= ?2")
        public void deleteByUserAndProduct(Long userId, Long productId);






}
