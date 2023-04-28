package com.hoopoe.mapper;


import com.hoopoe.domain.Order;
import com.hoopoe.domain.User;
import com.hoopoe.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user", target = "user", qualifiedByName = "getUser")
    OrderDTO orderToOrderDTO (Order order);

    @Named("getUser")
    public static String getUserName(User user){
        return user.getFirstName();
    }





}
