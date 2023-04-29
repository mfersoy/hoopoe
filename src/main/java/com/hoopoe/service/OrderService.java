package com.hoopoe.service;

import com.hoopoe.domain.CartItem;
import com.hoopoe.domain.Order;
import com.hoopoe.domain.OrderDetail;
import com.hoopoe.domain.User;
import com.hoopoe.dto.OrderDTO;
import com.hoopoe.dto.request.OrderRequest;
import com.hoopoe.exception.ResourceNotFoundException;
import com.hoopoe.exception.message.ErrorMessage;
import com.hoopoe.mapper.OrderMapper;
import com.hoopoe.repository.CartItemRepository;
import com.hoopoe.repository.OrderDetailRepository;
import com.hoopoe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

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

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderMapper orderMapper;


    public void addOrder(OrderRequest tables){

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
        cartItemRepository.deleteAll();

    }

    public List<OrderDTO> getAllOrders(){

        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOS= new ArrayList<>();
        orders.forEach(order ->orderDTOS.add(orderMapper.orderToOrderDTO(order)));
        return  orderDTOS;
    }

    public Page<OrderDTO> findOrderPageByUser(User user, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAllByUser(user, pageable);
        return getOrderDTOPage(orderPage);
    }

    private Page<OrderDTO> getOrderDTOPage(Page<Order> orderPage) {
        Page<OrderDTO> orderDTOPage = orderPage.map(new Function<Order, OrderDTO>() {
            @Override
            public OrderDTO apply(Order order) {
                return orderMapper.orderToOrderDTO(order);
            }
        });
        return orderDTOPage;
    }

    public OrderDTO findByIdAndUser(Long id, User user) {
        Order order = orderRepository.findByIdAndUser(id, user).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        return orderMapper.orderToOrderDTO(order);
    }

    public void removeOrderById(Long id){
        boolean exist = orderRepository.existsById(id);
        if(!exist){
            throw new ResourceNotFoundException(String.format((ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE)));
        }
        orderRepository.deleteById(id);
    }




}
