package com.hoopoe.service;

import com.hoopoe.domain.Category;
import com.hoopoe.domain.enums.CategoryType;
import com.hoopoe.exception.ResourceNotFoundException;
import com.hoopoe.exception.message.ErrorMessage;
import com.hoopoe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findByType(CategoryType categoryType){
        return categoryRepository.findByType(categoryType).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_EXCEPTION, categoryType.name())));
    }
}
