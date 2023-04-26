package com.hoopoe.service;

import com.hoopoe.domain.CartItem;
import com.hoopoe.domain.Order;
import com.hoopoe.domain.OrderDetail;
import com.hoopoe.domain.User;
import com.hoopoe.dto.request.TableRequest;
import com.hoopoe.repository.OrderDetailRepository;
import com.hoopoe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class OrderService {
    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;


    public void addOrder(TableRequest tables){

        Order order = new Order();
        User user = userService.getCurrentUser();
        LocalDateTime localDateTime= LocalDateTime.now();
        Set<OrderDetail> orderDetailSet = new HashSet<>();

        List<CartItem> cartItems = shoppingCartService.listCartItems(user);

        for(CartItem cartItem : cartItems){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setUser(user);
            orderDetailRepository.save(orderDetail);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByUser(user);

        for(OrderDetail orderDetail : orderDetailList){
            orderDetailSet.add(orderDetail);
        }

        Double estimatedTotal = 0.0D;
        for(CartItem item : cartItems){
            estimatedTotal += item.getProduct().getPrice();
        }


        order.setTables(tables.getTable());
        order.setFirstName(user.getFirstName());
        order.setLastName(user.getLastName());
        order.setOrderDetailSet(orderDetailSet);
        order.setLocalDateTime(localDateTime);
        order.setTotalPrice(estimatedTotal);
        order.setUser(user);

        orderRepository.save(order);




    }
}
