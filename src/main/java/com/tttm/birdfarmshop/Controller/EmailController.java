package com.tttm.birdfarmshop.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tttm.birdfarmshop.Exception.CustomException;
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
@RequestMapping("/email")
public class EmailController {
    private final MailService mailService;

    @PostMapping("/forgotPassword")
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

}
