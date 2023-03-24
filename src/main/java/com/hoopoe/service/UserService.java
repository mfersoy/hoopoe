package com.hoopoe.service;

import com.hoopoe.domain.Role;
import com.hoopoe.domain.User;
import com.hoopoe.domain.enums.RoleType;
import com.hoopoe.dto.UserDTO;
import com.hoopoe.dto.request.RegisterRequest;
import com.hoopoe.exception.BadRequestException;
import com.hoopoe.exception.ConflictException;
import com.hoopoe.exception.ResourceNotFoundException;
import com.hoopoe.exception.message.ErrorMessage;
import com.hoopoe.mapper.UserMapper;
import com.hoopoe.repository.UserRepository;
import com.hoopoe.security.SecurityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

   private final UserRepository userRepository;

   private final RoleService roleService;

   private final PasswordEncoder passwordEncoder;

   private final UserMapper userMapper;

    public UserService(UserRepository userRepository,RoleService roleService, @Lazy PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository=userRepository;
        this.roleService=roleService;
        this.passwordEncoder=passwordEncoder;
        this.userMapper=userMapper;
    }

    public User getUserByEmail(String email){

       return userRepository.findByEmail(email).
               orElseThrow(() -> new ResourceNotFoundException(
                       String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,email)));


    }

    public void saveUser(RegisterRequest registerRequest){

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ConflictException(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE);
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

            User user = new User();
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(encodedPassword);
            user.setPhoneNumber(registerRequest.getPhoneNumber());
            user.setRoles(roles);
            userRepository.save(user);

    }

    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = userMapper.map(users);
        return  userDTOS;
    }

    public UserDTO getPrincipal(){
        User currentUser =getCurrentUser();
        UserDTO userDTO = userMapper.userToUserDTO(currentUser);
        return userDTO;
    }
    //I FETCHED CURRENT USER INFORMATION FROM SECURITY CONTEXT
    public User getCurrentUser(){
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessage.PRINCIPAL_NOT_FOUND_MESSAGE)));
        User user = getUserByEmail(email);
        return user;
    }

    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,id)));
        return userMapper.userToUserDTO(user);
    }

    public User getById(Long id){
        User user = userRepository.findUserById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
        return user;
    }

    public void removeUserById(Long id){
        User user = getById(id);
        if(user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
       userRepository.deleteById(user.getId());
    }

}
