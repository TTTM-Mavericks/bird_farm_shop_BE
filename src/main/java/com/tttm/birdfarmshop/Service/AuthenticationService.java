package com.tttm.birdfarmshop.Service;


import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Utils.AuthenticationRequest;
import com.tttm.birdfarmshop.Utils.AuthenticationResponse;
import com.tttm.birdfarmshop.exception.CustomException;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    AuthenticationResponse register(User dto) throws CustomException;

   AuthenticationResponse login(AuthenticationRequest dto) throws CustomException;
}
