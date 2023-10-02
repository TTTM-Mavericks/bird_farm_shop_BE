package com.tttm.birdfarmshop.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tttm.birdfarmshop.Exception.CustomException;
import com.tttm.birdfarmshop.Service.CodeStorageService;
import com.tttm.birdfarmshop.Service.MailService;
import com.tttm.birdfarmshop.DTO.MailDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final MailService mailService;
    private final CodeStorageService codeStorageService;
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> ForgotPassword(@RequestBody String json) throws CustomException
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            MailDTO dto = mapper.readValue(json, MailDTO.class);
            return new ResponseEntity<>(mailService.ForgotPassword(dto.getEmail()), HttpStatus.OK);
        }
        catch (JsonProcessingException ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PostMapping("/sendCode")
    public ResponseEntity<String> sendCode(@RequestBody String json, HttpSession session) throws CustomException
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            MailDTO dto = mapper.readValue(json, MailDTO.class);

            // Generate Code to Send Mail
            String code = mailService.SendCode(dto.getEmail());
            dto.setCode(code);

            // Store Code in Session exist in 60 seconds
            codeStorageService.storeCodeInSession(dto, session);
            return new ResponseEntity<>(code, HttpStatus.OK);
        }
        catch (JsonProcessingException ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestBody String json, HttpSession session) throws CustomException
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            MailDTO dto = mapper.readValue(json, MailDTO.class);
            return new ResponseEntity<>(codeStorageService.getCodeFromSession(dto, session), HttpStatus.OK);
        }
        catch (JsonProcessingException ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        }
    }

}
