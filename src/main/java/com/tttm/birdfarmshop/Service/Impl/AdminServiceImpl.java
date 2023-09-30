package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.Models.Admin;
import com.tttm.birdfarmshop.Models.Customer;
import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Repository.AdminRepository;
import com.tttm.birdfarmshop.Service.AdminService;
import com.tttm.birdfarmshop.Service.Impl.ServiceMsg.ConstantMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
    @Override
    public void createAdmin(User user) {
        Admin customer = new Admin(user.getUserID());
        adminRepository.save(customer);
    }
}
