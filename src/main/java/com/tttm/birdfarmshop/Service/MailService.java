package com.tttm.birdfarmshop.Service;


import com.tttm.birdfarmshop.Utils.Response.AuthenticationResponse;

public interface MailService {

    AuthenticationResponse ForgotPassword(String Email);

    String SendCode(String Email);
}

