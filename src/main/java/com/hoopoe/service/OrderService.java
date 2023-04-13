package com.hoopoe.service;


import com.hoopoe.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;




}
