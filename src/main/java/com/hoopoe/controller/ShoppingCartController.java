package com.hoopoe.controller;

import com.hoopoe.domain.CartItem;
import com.hoopoe.domain.User;
import com.hoopoe.service.ShoppingCartService;
import com.hoopoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService service;

    @GetMapping("/cart")
    public String viewCart(Model model, String table){
        User user = userService.getCurrentUser();

        List<CartItem> cartItems = service.listCartItems(user);

        Double estimatedTotal = 0.0D;
        for(CartItem item : cartItems){
                estimatedTotal += item.getProduct().getPrice();
        }
        model.addAttribute("",cartItems);
        model.addAttribute("",estimatedTotal);
        model.addAttribute("", table);

        return "cart/shopping_cart";

    }


}
