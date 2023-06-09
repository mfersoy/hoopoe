package com.hoopoe.repository;

import com.hoopoe.domain.Product;
import com.hoopoe.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query("SELECT count(*) from Product  p JOIN  p.image im WHERE im.id=:id")
//    Integer findProductCountByImageId(@Param("id") String id);

    @Query("SELECT p from Product p JOIN p.image im where im.id=:id")
    List<Product> findProductByImageId(@Param("id") String id);

    @EntityGraph(attributePaths = {"image"})
    List<Product> findAll();

    @EntityGraph(attributePaths = "image")
    Optional<Product> findProductById(Long id);

    @EntityGraph(attributePaths = {"image"})
    Page<Product> findAll(Pageable pageable);




}
