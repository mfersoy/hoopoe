package com.hoopoe.mapper;

import com.hoopoe.domain.Order;
import com.hoopoe.dto.request.OrderRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order orderRequestToOrder(OrderRequest orderRequest);
}
