package com.hoopoe.controller;

import com.hoopoe.domain.Product;
import com.hoopoe.domain.User;
import com.hoopoe.dto.request.OrderRequest;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.service.OrderService;
import com.hoopoe.service.ProductService;
import com.hoopoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping("save/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('WORKER')")
    public ResponseEntity<HResponse> saveOrder(@RequestBody @Valid OrderRequest orderRequest){
        User user = userService.getCurrentUser();
        Set<Product> productSet;

//        orderService.createOrder(orderRequest,user);
        HResponse hResponse = new HResponse(ResponseMessage.ORDER_IS_SAVED,true);
        return ResponseEntity.ok(hResponse);
    }

}
