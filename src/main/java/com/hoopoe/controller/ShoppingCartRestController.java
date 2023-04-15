package com.hoopoe.controller;

import com.hoopoe.domain.User;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.service.ShoppingCartService;
import com.hoopoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shoppingcart")
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    UserService userService;

    //http://localhost:8080/shoppingcart/cart/add/1?quantity=2
    @PostMapping("cart/add/{productId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER') OR hasRole('WORKER')")
    public ResponseEntity<HResponse> addProductToCart(@PathVariable Long productId, @RequestParam("quantity") Integer quantity){
        User user = userService.getCurrentUser();
        cartService.addProduct(productId,quantity,user);

        HResponse hResponse = new HResponse(ResponseMessage.PRODUCT_ADD_TO_SHOPPING_CART,true);
        return  ResponseEntity.ok(hResponse);
    }


}
