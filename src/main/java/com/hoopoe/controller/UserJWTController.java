package com.hoopoe.controller;

import com.hoopoe.dto.request.LoginRequest;
import com.hoopoe.dto.request.RegisterRequest;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.LoginResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.security.jwt.JwtUtils;
import com.hoopoe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserJWTController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;



    @PostMapping("/register")
    public ResponseEntity<HResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        userService.saveUser(registerRequest);

        HResponse hResponse = new HResponse();
        hResponse.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        hResponse.setSuccess(true);
        return new ResponseEntity<>(hResponse, HttpStatus.CREATED) ;

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetails userDetails= (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateJwtToken(userDetails);


        LoginResponse loginResponse=new LoginResponse(jwtToken);

        return new ResponseEntity<>(loginResponse,HttpStatus.OK);

    }



}
