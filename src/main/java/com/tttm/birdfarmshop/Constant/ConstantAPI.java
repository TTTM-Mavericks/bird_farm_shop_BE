package com.tttm.birdfarmshop.Constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantAPI {
    // Request Mapping
    public static final String CUSTOMER = "/customer";
    public static final String SHIPPER = "/shipper";
    public static final String SELLER = "/seller";
    public static final String ADMIN = "/admin";
    public static final String HEALTH_CARE_PROFESSIONAL = "/healthCareProfestional";
    public static final String AUTH = "/auth";
    public static final String EMAIL = "/email";

    // Function
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String FORGOT_PASSWORD = "/forgotPassword";
    public static final String SEND_CODE = "/sendCode";
    public static final String VERIFY_CODE = "/verifyCode";
    public static final String GET_ALL_CUSTOMERS = "/getAllCustomers";
    public static final String GET_ALL_USERS = "/getAllUsers";
    public static final String BAN_USER_ACCOUNT = "/banUserAccount";
    public static final String UNBAN_USER_ACCOUNT = "/unBanUserAccount";
}
