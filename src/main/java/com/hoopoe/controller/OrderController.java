package com.hoopoe.controller;

import com.hoopoe.domain.Product;
import com.hoopoe.domain.User;
import com.hoopoe.dto.ProductDTO;
import com.hoopoe.dto.request.OrderRequest;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.mapper.ProductMapper;
import com.hoopoe.service.OrderService;
import com.hoopoe.service.ProductService;
import com.hoopoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;




    @PostMapping("/save")
    @PreAuthorize(("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('WORKER')"))
    public ResponseEntity<HResponse> makeOrder(@RequestBody @Valid OrderRequest orderRequest){
        User user= userService.getCurrentUser();

       orderService.createOrder(orderRequest,user);


        return null;
    }

    @PostMapping("/set/{id}")
    public ResponseEntity<HResponse> addToProductSet(@PathVariable Long id){
        ProductDTO productDTO = productService.findById(id);
        Product product = productMapper.productDTOToProduct(productDTO);
        OrderRequest orderRequest= new OrderRequest();
        orderRequest.addProductToSet(product);
        HResponse hResponse = new HResponse(ResponseMessage.PRODUCT_ADDED_TO_SET,true);

        return ResponseEntity.ok(hResponse);
    }


}
