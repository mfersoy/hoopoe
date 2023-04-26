package com.hoopoe.controller;

import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER') OR hasRole('WORKER')")
    public ResponseEntity<HResponse> addOrder(@RequestBody @Valid String table){

        orderService.addOrder(table);
        HResponse response = new HResponse(ResponseMessage.ORDER_IS_SAVED,true);
        return ResponseEntity.ok(response);
    }


}
