package com.hoopoe.service;

import com.hoopoe.domain.Role;
import com.hoopoe.domain.User;
import com.hoopoe.domain.enums.RoleType;
import com.hoopoe.dto.UserDTO;
import com.hoopoe.dto.request.AdminUserUpdateRequests;
import com.hoopoe.dto.request.RegisterRequest;
import com.hoopoe.dto.request.UpdatePasswordRequest;
import com.hoopoe.dto.request.UserUpdateRequest;
import com.hoopoe.exception.BadRequestException;
import com.hoopoe.exception.ConflictException;
import com.hoopoe.exception.ResourceNotFoundException;
import com.hoopoe.exception.message.ErrorMessage;
import com.hoopoe.mapper.UserMapper;
import com.hoopoe.repository.UserRepository;
import com.hoopoe.security.SecurityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

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

    public Page<UserDTO> getUserPage(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);

        return getUserDTOPage(userPage);
    }
    private Page<UserDTO> getUserDTOPage(Page<User> userPage) {
        Page<UserDTO> userDTOPage = userPage.map(new Function<User, UserDTO>() {
            @Override
            public UserDTO apply(User user) {
                return userMapper.userToUserDTO(user);
            }
        });
        return userDTOPage;
    }

    @Transactional
    public void updateUser(UserUpdateRequest userUpdateRequest){
        User user = getCurrentUser();

        if(user.getBuiltIn()){
            throw  new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        boolean emailExist= userRepository.existsByEmail(userUpdateRequest.getEmail());

        if(emailExist && !userUpdateRequest.getEmail().equals(user.getEmail())){
             throw  new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,userUpdateRequest.getEmail()));
        }

        userRepository.update(user.getId(), userUpdateRequest.getFirstName(), userUpdateRequest.getLastName(), userUpdateRequest.getPhoneNumber(), userUpdateRequest.getEmail());

    }

    public void updateUserAuth(Long id, AdminUserUpdateRequests adminUserUpdateRequests){
        User user = getById(id);

        if(user.getBuiltIn()){
            throw  new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        boolean emailExist = userRepository.existsByEmail(adminUserUpdateRequests.getEmail());

        if(emailExist && !adminUserUpdateRequests.getEmail().equals(user.getEmail())){
             throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,adminUserUpdateRequests.getEmail()));
        }

        if(adminUserUpdateRequests.getPassword()==null){
            adminUserUpdateRequests.setPassword(user.getPassword());
        } else {
            String encodedPassword = passwordEncoder.encode(adminUserUpdateRequests.getPassword());
            adminUserUpdateRequests.setPassword(encodedPassword);
        }

        Set<String> userStrRoles = adminUserUpdateRequests.getRoles();
        Set<Role>  roles = convertRoles(userStrRoles);
        user.setFirstName(adminUserUpdateRequests.getFirstName());
        user.setLastName(adminUserUpdateRequests.getLastName());
        user.setEmail(adminUserUpdateRequests.getEmail());
        user.setPassword(adminUserUpdateRequests.getPassword());
        user.setPhoneNumber(adminUserUpdateRequests.getPhoneNumber());
        user.setBuiltIn(adminUserUpdateRequests.getBuiltIn());
        user.setRoles(roles);
        userRepository.save(user);

    }

    public Set<Role> convertRoles(Set<String>pRoles){
        Set<Role> roles = new HashSet<>();

        if(pRoles==null){
            Role userRole = roleService.findByType(RoleType.ROLE_CUSTOMER);
            roles.add(userRole);
        }else {
            pRoles.forEach(roleStr -> {
                if(roleStr.equals(RoleType.ROLE_ADMIN.getName())){
                    Role adminRole = roleService.findByType(RoleType.ROLE_ADMIN);
                    roles.add(adminRole);
                }else {
                    Role userRole = roleService.findByType(RoleType.ROLE_CUSTOMER);
                    roles.add(userRole);
                }
            });
        }
        return roles;
    }

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest){
        User user = getCurrentUser();

        if(user.getBuiltIn()){
            throw  new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if(!passwordEncoder.matches(updatePasswordRequest.getOldPassword(),user.getPassword())){
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED);
        }

        String hashedPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }


}
