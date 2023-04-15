package com.hoopoe.service;

import com.hoopoe.controller.ProductController;
import com.hoopoe.domain.CartItem;
import com.hoopoe.domain.Product;
import com.hoopoe.domain.User;
import com.hoopoe.dto.ProductDTO;
import com.hoopoe.mapper.ProductMapper;
import com.hoopoe.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductMapper productMapper;

    public void addProduct(Long productId, Integer quantity, User user){
        Integer updatedQuantity= quantity;

        ProductDTO productDTO = productService.findById(productId);
        Product product = productMapper.productDTOToProduct(productDTO);

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user,product);

        if (cartItem!=null){
            updatedQuantity=cartItem.getQuantity() + quantity;
            cartItem.setQuantity(updatedQuantity);
        }else{
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }

        cartItem.setQuantity(updatedQuantity);
        cartItemRepository.save(cartItem);

    }

    public List<CartItem> listCartItems(User user){
        return cartItemRepository.findByUser(user);

    }
}
