package com.hoopoe.service;

import com.hoopoe.domain.Product;
import com.hoopoe.domain.User;
import com.hoopoe.dto.request.OrderRequest;
import com.hoopoe.mapper.OrderMapper;
import com.hoopoe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderMapper mapper;

    public void createOrder(OrderRequest orderRequest, User user, List<Product> productList){

    }

}
