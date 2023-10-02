package com.tttm.birdfarmshop.Controller;


import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Service.AdminService;
import com.tttm.birdfarmshop.Service.AuthenticationService;
import com.tttm.birdfarmshop.Utils.Request.AuthenticationRequest;
import com.tttm.birdfarmshop.Utils.Response.AuthenticationResponse;
import com.tttm.birdfarmshop.Exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AuthenticationService authenticationService;
    private final AdminService adminService;

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

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<User>> getAllCustomers() throws CustomException {
        try {
            return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() throws CustomException {
        try {
            return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/BanUserAccount/{UserID}")
    public ResponseEntity<User> BanUserAccount(@PathVariable("UserID") int UserID) throws CustomException {
        try {
            return new ResponseEntity<>(adminService.BanUserAccount(UserID), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/UnBanUserAccount/{UserID}")
    public ResponseEntity<User> UnBanUserAccount(@PathVariable("UserID") int UserID) throws CustomException {
        try {
            return new ResponseEntity<>(adminService.UnBanUserAccount(UserID), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
