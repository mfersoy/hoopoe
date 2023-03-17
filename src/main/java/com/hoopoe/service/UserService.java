package com.hoopoe.service;

import com.hoopoe.domain.User;
import com.hoopoe.exception.ResourceNotFoundException;
import com.hoopoe.exception.message.ErrorMessage;
import com.hoopoe.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User getUserByEmail(String email){

       return userRepository.findByEmail(email).
               orElseThrow(() -> new ResourceNotFoundException(
                       String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,email)));


    }
}
