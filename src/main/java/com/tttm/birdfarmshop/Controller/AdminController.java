package com.tttm.birdfarmshop.Controller;


import com.tttm.birdfarmshop.Service.AuthenticationService;
import com.tttm.birdfarmshop.Utils.AuthenticationRequest;
import com.tttm.birdfarmshop.Utils.AuthenticationResponse;
import com.tttm.birdfarmshop.Exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest user) throws CustomException {
        try {
            return new ResponseEntity<>(authenticationService.login(user), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
