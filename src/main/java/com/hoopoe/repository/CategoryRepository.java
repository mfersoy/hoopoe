package com.hoopoe.repository;

import com.hoopoe.domain.Category;
import com.hoopoe.domain.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {



    Optional<Category> findByType(CategoryType type);
}
