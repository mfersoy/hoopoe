package com.hoopoe.controller;

import com.hoopoe.domain.OrderStatus;
import com.hoopoe.domain.User;
import com.hoopoe.domain.enums.OrderStatusType;
import com.hoopoe.dto.OrderDTO;
import com.hoopoe.dto.request.OrderRequest;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.service.OrderService;
import com.hoopoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private UserService userService;

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

    @GetMapping("/auth/all")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<Page<OrderDTO>> getAllUserOrders(@RequestParam("page") int page,
                                                                       @RequestParam("size") int size,
                                                                       @RequestParam("sort") String prop,
                                                                       @RequestParam(value = "direction", required = false, defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        User user = userService.getCurrentUser();
        Page<OrderDTO> orderDTOSPage = orderService.findOrderPageByUser(user, pageable);

        return ResponseEntity.ok(orderDTOSPage);
    }

    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> getUserOrderById(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        OrderDTO orderDTO = orderService.findByIdAndUser(id, user);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("status/auth")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('WORKER')")
    public ResponseEntity<List<OrderDTO>> getUserOrdersByType(@RequestBody @Valid String status){
        User user= userService.getCurrentUser();
        List<OrderDTO> orderDTOS = orderService.getUserOrdersByStatus(status);
        return ResponseEntity.ok(orderDTOS);
    }


    @DeleteMapping("/admin/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HResponse> deleteOrder(@PathVariable Long id){
        orderService.removeOrderById(id);
        HResponse hResponse= new HResponse(ResponseMessage.ORDER_ROMEVED,true);
        return  ResponseEntity.ok(hResponse);
    }




}
