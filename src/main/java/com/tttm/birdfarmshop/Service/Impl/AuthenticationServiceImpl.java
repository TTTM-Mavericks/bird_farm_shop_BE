package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.Enums.ERole;
import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Repository.UserRepository;
import com.tttm.birdfarmshop.Service.*;
import com.tttm.birdfarmshop.Constant.ConstantMessage;
import com.tttm.birdfarmshop.Utils.Request.AuthenticationRequest;
import com.tttm.birdfarmshop.Utils.Response.AuthenticationResponse;
import com.tttm.birdfarmshop.Exception.CustomException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);
    @Override
    public AuthenticationResponse register(User dto, HttpSession session) throws CustomException
    {
        ERole role = null;
        switch (dto.getRole().toString().toUpperCase())
        {
            case "CUSTOMER":
                role = ERole.CUSTOMER;
                break;
            case "ADMINISTRATOR":
                role = ERole.ADMINISTRATOR;
                break;
            case "SHIPPER":
                role = ERole.SHIPPER;
                break;
            case "SELLER":
                role = ERole.SELLER;
                break;
            case "HEALTHCAREPROFESSIONAL":
                role = ERole.HEALTHCAREPROFESSIONAL;
                break;
        }
        if (userRepository.findUserByEmail(dto.getEmail()) != null)
        {
            logger.warn(ConstantMessage.EMAIL_IS_EXIST);
            throw new CustomException(ConstantMessage.EMAIL_IS_EXIST.toString());
        }
        if (userRepository.findUserByPhone(dto.getPhone()) != null)
        {
            logger.warn(ConstantMessage.PHONE_IS_EXIST);
            throw new CustomException(ConstantMessage.PHONE_IS_EXIST.toString());
        }
        User user = new User(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhone(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getGender(),
                dto.getDateOfBirth(),
                dto.getAddress(),
                false,
                role
        );
        var jwtToken = jwtService.generateToken(user);

        session.setAttribute(dto.getEmail(), user);
        session.setAttribute(user.toString(), jwtToken);

        return new AuthenticationResponse("Move to Send Code to Email");
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest dto) throws CustomException {
        User user = userRepository.findUserByEmailAndActiveStatus(dto.getEmail(), false);
        if(user == null)
        {
            logger.warn(ConstantMessage.INVALID_USERNAME_OR_PASSWORD.toString());
            throw new CustomException(ConstantMessage.INVALID_USERNAME_OR_PASSWORD.toString());
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        var jwtToken = jwtService.generateToken(user);

        logger.info("Login " + ConstantMessage.SUCCESS);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
