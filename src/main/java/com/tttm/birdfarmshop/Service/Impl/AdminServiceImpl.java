package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.Enums.AccountStatus;
import com.tttm.birdfarmshop.Models.Admin;
import com.tttm.birdfarmshop.Enums.ERole;
import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Repository.AdminRepository;
import com.tttm.birdfarmshop.Repository.UserRepository;
import com.tttm.birdfarmshop.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

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
    public List<User> getAllUsers() {
        List<User> list = userRepository.getAllUsers();
        logger.info("Get All Users");
        return list;
    }

    @Override
    public List<User> getAllCustomers() {
        List<User> list = userRepository.getAllUsersBasedOnRole(ERole.CUSTOMER.toString());
        logger.info("Get All Customers");
        return list;
    }

    @Override
    public User BanUserAccount(int UserID) {
        User user = userRepository.findById(UserID).get();
        if(user != null)
        {
            user.setAccountStatus(AccountStatus.INACTIVE);
            userRepository.save(user);
            logger.info("Ban User with User ID {} Successfully", UserID);
        }
        return user;
    }

    @Override
    public User UnBanUserAccount(int UserID) {
        User user = userRepository.findById(UserID).get();
        if(user != null)
        {
            user.setAccountStatus(AccountStatus.ACTIVE);
            userRepository.save(user);
            logger.info("UnBan User with User ID {} Successfully", UserID);
        }
        return user;
    }
}
