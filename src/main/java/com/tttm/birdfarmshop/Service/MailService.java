package com.tttm.birdfarmshop.Service;


import com.tttm.birdfarmshop.Utils.Request.CancelOrderRequest;
import com.tttm.birdfarmshop.Utils.Response.AuthenticationResponse;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;

public interface MailService {

    MessageResponse ForgotPassword(String Email);
    String SendCode(String Email);
    MessageResponse sendMailForCancelOrder(CancelOrderRequest cancelOrderRequest);
}

