package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.DTO.MailDTO;
import com.tttm.birdfarmshop.Enums.ERole;
import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Repository.UserRepository;
import com.tttm.birdfarmshop.Service.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CodeStorageServiceImpl implements CodeStorageService {

    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final Map<String, Long> EmailAndExpiration = new HashMap<>(); //Store Email and Expiration Of Code
    private final Map<String, String> EmailAndCode = new HashMap<>(); //Store Email and Code

    private final CustomerService customerService;

    private final AdminService adminService;

    private final ShipperService shipperService;

    private final SellerService sellerService;

    private final HealthcareProfessionalService healthcareProfessionalService;
    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);
    @Override
    public void storeCodeInSession(MailDTO dto, HttpSession session) {
        EmailAndCode.put(dto.getEmail(), dto.getCode());
        Long expirationTime = System.currentTimeMillis() + (60 * 1000);
        EmailAndExpiration.put(dto.getEmail(), expirationTime);
    }

    @Override
    public String getCodeFromSession(MailDTO dto, HttpSession session) {
        Long expirationTime = EmailAndExpiration.get(dto.getEmail());
        if(expirationTime != null && expirationTime < System.currentTimeMillis()) // Check the Expiration Time for each Keys store in KeySessions
        {
            EmailAndCode.remove(dto.getEmail());
            EmailAndExpiration.remove(dto.getEmail());
            return "The Code is expired.";
        }
        String code = (String) EmailAndCode.get(dto.getEmail());
        if(code.equals(dto.getCode())) // Compare Code from System with Customer
        {
            User user = (User) session.getAttribute(dto.getEmail());

            userRepository.save(user);

            var jwtToken = jwtService.generateToken(user);

            logger.info("Create new Email: {} Successfully", dto.getEmail());

            switch (user.getRole().name())
            {
                case "CUSTOMER":
                    customerService.createCustomer(user);
                    break;
                case "ADMINISTRATOR":
                    adminService.createAdmin(user);
                    break;
                case "SHIPPER":
                    shipperService.createShipper(user);
                    break;
                case "SELLER":
                    sellerService.createSeller(user);
                    break;
                case "HEALTHCAREPROFESSIONAL":
                    healthcareProfessionalService.createHealthcareProfessional(user);
                    break;
            }

            EmailAndCode.remove(dto.getEmail());
            EmailAndExpiration.remove(dto.getEmail());
            session.removeAttribute(dto.getEmail());
            session.removeAttribute(user.toString());

            return jwtToken.toString();
        }
        return "Invalid Code";
    }
}
