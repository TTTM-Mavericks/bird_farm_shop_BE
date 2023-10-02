package com.tttm.birdfarmshop.Service;


import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Utils.Request.AuthenticationRequest;
import com.tttm.birdfarmshop.Utils.Response.AuthenticationResponse;
import com.tttm.birdfarmshop.Exception.CustomException;
import jakarta.servlet.http.HttpSession;

public interface AuthenticationService {
    AuthenticationResponse register(User dto, HttpSession session) throws CustomException;

   AuthenticationResponse login(AuthenticationRequest dto) throws CustomException;
}
