package com.hoopoe.controller;

import com.hoopoe.dto.UserDTO;
import com.hoopoe.dto.request.AdminUserUpdateRequests;
import com.hoopoe.dto.request.UpdatePasswordRequest;
import com.hoopoe.dto.request.UserUpdateRequest;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {


    private final  UserService userService;

    //http://localhost:8080/user/auth/all
    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("/auth/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
    //http://localhost:8080/user
    @PreAuthorize("(hasRole('ADMIN') OR hasRole('CUSTOMER'))")
    @GetMapping
    public ResponseEntity<UserDTO> getUser(){
        UserDTO userDTO = userService.getPrincipal();
        return ResponseEntity.ok(userDTO);
    }

    //http://localhost:8080/user/1/auth
    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("{id}/auth")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    //http://localhost:8080/user/auth?id=2
    @PreAuthorize("(hasRole('ADMIN'))")
    @DeleteMapping("/auth")
    public ResponseEntity<HResponse> deleteUser(@RequestParam("id")  Long id){
        userService.removeUserById(id);

        HResponse response = new HResponse();
        response.setMessage(ResponseMessage.USER_DELETE_RESPONSE__MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    //http://localhost:8080/user/auth/pages?page=0&size=2&sort=id&direction=ASC
    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("/auth/pages")
    public ResponseEntity<Page<UserDTO>> getUsersByPage(@RequestParam("page") int page,
                                                        @RequestParam("size") int size,
                                                        @RequestParam("sort") String prop,
                                                        @RequestParam(value = "direction", required = false,
                                                        defaultValue = "DESC") Direction direction){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<UserDTO> userDTOPage = userService.getUserPage(pageable);

        return ResponseEntity.ok(userDTOPage);
    }

    //http://localhost:8080/user
    @PreAuthorize("(hasRole('ADMIN') or hasRole('CUSTOMER'))")
    @PutMapping()
    public ResponseEntity<HResponse> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest){
       userService.updateUser(userUpdateRequest);
       HResponse response = new HResponse();
       response.setMessage(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE);
       response.setSuccess(true);
       return  ResponseEntity.ok(response);
    }


    //http://localhost:8080/user/3/auth
    @PreAuthorize("(hasRole('ADMIN'))")
    @PutMapping("/{id}/auth")
    public ResponseEntity<HResponse> updateUserAuth(@PathVariable Long id,
                                                    @Valid @RequestBody AdminUserUpdateRequests adminUserUpdateRequest){
        userService.updateUserAuth(id,adminUserUpdateRequest);
        HResponse response = new HResponse();
        response.setMessage(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);

    }

    //http://localhost:8080/user/auth

    @PatchMapping("/auth")
    public ResponseEntity<HResponse> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        userService.updatePassword(updatePasswordRequest);
        HResponse response = new HResponse();
        response.setSuccess(true);
        response.setMessage(ResponseMessage.USER_PASSWORD_CHANFED_MESSAGE);
        return  ResponseEntity.ok(response);
    }



}
