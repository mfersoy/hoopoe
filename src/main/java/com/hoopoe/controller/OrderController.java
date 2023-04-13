package com.hoopoe.controller;

import com.hoopoe.domain.Product;
import com.hoopoe.domain.User;
import com.hoopoe.dto.request.OrderRequest;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.service.OrderService;
import com.hoopoe.service.ProductService;
import com.hoopoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize(("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('WORKER')"))
    public ResponseEntity<HResponse> makeOrder(@RequestBody @Valid OrderRequest orderRequest){

        List<Product> productLis = orderRequest.getProducts();

        User user= userService.getCurrentUser();

        orderService.




        return null;
    }


}
