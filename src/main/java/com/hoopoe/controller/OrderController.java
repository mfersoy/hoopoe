package com.hoopoe.controller;

import com.hoopoe.dto.OrderDTO;
import com.hoopoe.dto.request.OrderRequest;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER') OR hasRole('WORKER')")
    public ResponseEntity<HResponse> addOrder(@RequestBody @Valid OrderRequest tables){

        orderService.addOrder(tables);
        HResponse response = new HResponse(ResponseMessage.ORDER_IS_SAVED,true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('WORKER')")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<OrderDTO> orderDTOS = orderService.getAllOrders();
        return ResponseEntity.ok(orderDTOS);
    }


}
