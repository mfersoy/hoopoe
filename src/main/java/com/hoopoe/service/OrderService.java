package com.hoopoe.service;

import com.hoopoe.domain.Order;
import com.hoopoe.domain.Product;
import com.hoopoe.domain.User;
import com.hoopoe.domain.enums.OrderStatus;
import com.hoopoe.dto.ProductDTO;
import com.hoopoe.dto.request.OrderRequest;
import com.hoopoe.mapper.OrderMapper;
import com.hoopoe.mapper.ProductMapper;
import com.hoopoe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    public void createOrder(OrderRequest orderRequest, User user){
        Order order =  new Order();

        order.setProducts(orderRequest.getProducts());
        order.setTable(orderRequest.getTable());
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);

        List<Double> prices = new ArrayList<>();

        orderRequest.getProducts().stream().map(p->prices.add(p.getPrice()));

        Double sum =0.0;

        for(Double num : prices ){
            sum = sum +num;
        }
        order.setTotalPrice(sum);


        repository.save(order);

    }







}
