package com.hoopoe.dto;

import com.hoopoe.domain.OrderDetail;
import com.hoopoe.domain.Product;
import com.hoopoe.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderDetailDTO {

    @Autowired
    private ProductMapper productMapper;

    private Long id;

    private ProductDTO product;

    private Integer quantity;

    private String user;




}
