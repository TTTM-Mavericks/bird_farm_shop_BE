package com.tttm.birdfarmshop.Controller;

import com.tttm.birdfarmshop.Constant.ConstantAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tttm.birdfarmshop.DTO.MailDTO;
import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Service.AuthenticationService;
import com.tttm.birdfarmshop.Service.CodeStorageService;
import com.tttm.birdfarmshop.Service.MailService;
import com.tttm.birdfarmshop.Utils.Request.AuthenticationRequest;
import com.tttm.birdfarmshop.Utils.Response.AuthenticationResponse;
import com.tttm.birdfarmshop.Exception.CustomException;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConstantAPI.AUTH)
public class AuthController {
    private final AuthenticationService authenticationService;

    private final MailService mailService;
  
    private final CodeStorageService codeStorageService;

    @PostMapping(ConstantAPI.REGISTER))
    public ResponseEntity<MessageResponse> register(@RequestBody User user, HttpSession session) throws Exception {
        try {
            return new ResponseEntity<>(codeStorageService.register(user, session), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(ConstantAPI.LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest user) throws CustomException {
        try {
            return new ResponseEntity<>(authenticationService.login(user), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<AuthenticationResponse> verifyCode(@RequestBody String json, HttpSession session) throws CustomException
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            MailDTO dto = mapper.readValue(json, MailDTO.class);
            return new ResponseEntity<>(codeStorageService.getCodeFromSession(dto, session), HttpStatus.OK);
        }
        catch (JsonProcessingException ex)
        {
            return new ResponseEntity<>(new AuthenticationResponse(ex.getMessage()), HttpStatus.NOT_IMPLEMENTED);
        }
    }

}
