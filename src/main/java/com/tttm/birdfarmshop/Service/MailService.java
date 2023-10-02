package com.tttm.birdfarmshop.Service;


public interface MailService {

    String ForgotPassword(String Email);

    String SendCode(String Email);

}

