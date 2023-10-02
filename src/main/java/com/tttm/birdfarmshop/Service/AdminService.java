package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.Models.User;

import java.util.List;

public interface AdminService {
    void createAdmin(User user);

    List<User> getAllUsers();

    List<User> getAllCustomers();

    User BanUserAccount(int UserID);

    User UnBanUserAccount(int UserID);
}
