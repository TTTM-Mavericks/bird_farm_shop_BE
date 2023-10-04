package com.tttm.birdfarmshop.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tttm.birdfarmshop.Constant.ConstantAPI;
import com.tttm.birdfarmshop.Exception.CustomException;
import com.tttm.birdfarmshop.Service.CodeStorageService;
import com.tttm.birdfarmshop.Service.MailService;
import com.tttm.birdfarmshop.DTO.MailDTO;
import com.tttm.birdfarmshop.Utils.Response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConstantAPI.EMAIL)
public class EmailController {
    private final MailService mailService;
    private final CodeStorageService codeStorageService;
    @PostMapping(ConstantAPI.FORGOT_PASSWORD)
    public ResponseEntity<AuthenticationResponse> ForgotPassword(@RequestBody String json) throws CustomException
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            MailDTO dto = mapper.readValue(json, MailDTO.class);
            return new ResponseEntity<>(mailService.ForgotPassword(dto.getEmail()), HttpStatus.OK);
        }
        catch (JsonProcessingException ex)
        {
            return new ResponseEntity<>(new AuthenticationResponse(ex.getMessage()), HttpStatus.NOT_IMPLEMENTED);
        }
    }

//    @PostMapping(ConstantAPI.SEND_CODE)
//    public ResponseEntity<String> sendCode(@RequestBody String json, HttpSession session) throws CustomException
//    {
//        try
//        {
//            ObjectMapper mapper = new ObjectMapper();
//            MailDTO dto = mapper.readValue(json, MailDTO.class);
//
//            User user = (User) session.getAttribute(dto.getEmail());
//            String token = (String) session.getAttribute(user.toString());
//            if(token != null)
//            {
//                // Generate Code to Send Mail
//                String code = mailService.SendCode(dto.getEmail());
//                dto.setCode(code);
//
//                // Store Code in Session exist in 60 seconds
//                codeStorageService.storeCodeInSession(dto, session);
//                return new ResponseEntity<>(code, HttpStatus.OK);
//            }
//        }
//        catch (JsonProcessingException ex)
//        {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
//        }
//        return new ResponseEntity<>("Empty Register", HttpStatus.OK);
//    }
//
//    @PostMapping(ConstantAPI.VERIFY_CODE)
//    public ResponseEntity<String> verifyCode(@RequestBody String json, HttpSession session) throws CustomException
//    {
//        try
//        {
//            ObjectMapper mapper = new ObjectMapper();
//            MailDTO dto = mapper.readValue(json, MailDTO.class);
//            return new ResponseEntity<>(codeStorageService.getCodeFromSession(dto, session), HttpStatus.OK);
//        }
//        catch (JsonProcessingException ex)
//        {
//            return new ResponseEntity<>(new AuthenticationResponse(ex.getMessage()), HttpStatus.NOT_IMPLEMENTED);
//        }
//    }

}
