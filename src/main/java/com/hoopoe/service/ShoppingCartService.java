package com.hoopoe.service;

import com.hoopoe.domain.CartItem;
import com.hoopoe.domain.Product;
import com.hoopoe.domain.User;
import com.hoopoe.dto.ProductDTO;
import com.hoopoe.mapper.ProductMapper;
import com.hoopoe.repository.CartItemRepository;
import com.hoopoe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductMapper productMapper;
    @Autowired
    private ProductRepository productRepository;

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

    //http://localhost:8080/shoppingcart/cart/update/2?quantity=5    *Update quantitiy and return total price
    public Double updateQuantity(Long productId,Integer quantity, User user){
        cartItemRepository.updateQuantity(quantity,user.getId(),productId);
        Product product = productRepository.findProductById(productId).get();
        Double price = product.getPrice() * quantity;
        return price;
    }

    //http://localhost:8080/shoppingcart/cart/remove/2
    public void removeProduct(Long productId, User user){
        cartItemRepository.deleteByUserAndProduct(user.getId(),productId);
    }
}
