package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.Enums.AccountStatus;
import com.tttm.birdfarmshop.Models.Admin;
import com.tttm.birdfarmshop.Enums.ERole;
import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Repository.AdminRepository;
import com.tttm.birdfarmshop.Repository.UserRepository;
import com.tttm.birdfarmshop.Service.AdminService;
import com.tttm.birdfarmshop.Utils.Response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final AdminRepository adminRepository;
    private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
    @Override
    public void createAdmin(User user) {
        Admin customer = new Admin(user.getUserID());
        adminRepository.save(customer);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> list = userRepository.getAllUsers();
        List<UserResponse> userResponseList = new ArrayList<>();
        for(User user : list)
        {
            userResponseList.add(convertToUserResponse(user));
        }
        logger.info("Get All Users");
        return userResponseList;
    }

    @Override
    public List<UserResponse> getAllCustomers() {
        List<User> list = userRepository.getAllUsersBasedOnRole(ERole.CUSTOMER.toString());
        logger.info("Get All Customers");
        List<UserResponse> userResponseList = new ArrayList<>();
        for(User user : list)
        {
            userResponseList.add(convertToUserResponse(user));
        }
        logger.info("Get All Users");
        return userResponseList;
    }

    @Override
    public UserResponse BanUserAccount(int UserID) {
        User user = userRepository.findById(UserID).get();
        if(user != null)
        {
            user.setAccountStatus(AccountStatus.INACTIVE);
            userRepository.save(user);
            logger.info("Ban User with User ID {} Successfully", UserID);
        }
        return convertToUserResponse(user);
    }

    @Override
    public UserResponse UnBanUserAccount(int UserID) {
        User user = userRepository.findById(UserID).get();
        if(user != null)
        {
            user.setAccountStatus(AccountStatus.ACTIVE);
            userRepository.save(user);
            logger.info("UnBan User with User ID {} Successfully", UserID);
        }
        return convertToUserResponse(user);
    }

    private UserResponse convertToUserResponse(User user)
    {
        return UserResponse.builder()
                .userID(user.getUserID())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .accountStatus(user.getAccountStatus())
                .role(user.getRole())
                .build();
    }
}
