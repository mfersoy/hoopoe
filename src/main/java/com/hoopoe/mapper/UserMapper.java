package com.hoopoe.mapper;

import com.hoopoe.domain.User;
import com.hoopoe.dto.UserDTO;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    List<UserDTO> map(List<User> userList);

}
