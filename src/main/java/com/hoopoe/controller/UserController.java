package com.hoopoe.controller;

import com.hoopoe.dto.UserDTO;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping("/auth/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
    @GetMapping
    public ResponseEntity<UserDTO> getUser(){
        UserDTO userDTO = userService.getPrincipal();
        return ResponseEntity.ok(userDTO);
    }


    @GetMapping("{id}/auth")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/auth")
    public ResponseEntity<HResponse> deleteUser(@RequestParam("id")  Long id){
        userService.removeUserById(id);

        HResponse response = new HResponse();
        response.setMessage(ResponseMessage.USER_DELETE_RESPONSE__MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }



}
