package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Utils.Response.UserResponse;

import java.util.List;

public interface AdminService {
    void createAdmin(User user);

    List<UserResponse> getAllUsers();

    List<UserResponse> getAllCustomers();

    UserResponse BanUserAccount(int UserID);

    UserResponse UnBanUserAccount(int UserID);
}
