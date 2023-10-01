package com.tttm.birdfarmshop.Service;


import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Utils.AuthenticationRequest;
import com.tttm.birdfarmshop.Utils.AuthenticationResponse;
import com.tttm.birdfarmshop.Exception.CustomException;

public interface AuthenticationService {
    AuthenticationResponse register(User dto) throws CustomException;

   AuthenticationResponse login(AuthenticationRequest dto) throws CustomException;
}
