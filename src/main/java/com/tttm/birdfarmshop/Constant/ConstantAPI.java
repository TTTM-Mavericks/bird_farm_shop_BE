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
    public static final String TYPE_OF_BIRD = "/typeOfBird";
    public static final String FOOD = "/food";
    public static final String NEST = "/nest";
    public static final String BIRD = "/bird";

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

    // Type Of Bird Function
    public static final String ADD_TYPE_OF_BIRD = "/addTypeOfBird";
    public static final String UPDATE_TYPE_OF_BIRD = "/updateTypeOfBird";
    public static final String GET_TYPE_OF_BIRD_BY_ID = "/getTypeOfBirdByID";
    public static final String GET_ALL_TYPE_OF_BIRD = "/getAllTypeOfBird";

    // Food Function
    public static final String ADD_FOOD = "/addFood";
    public static final String UPDATE_FOOD = "/updateFood";
    public static final String GET_FOOD_BY_ID = "/getFoodByID";
    public static final String GET_ALL_FOOD = "/getAllFood";

    // Nest Function
    public static final String ADD_NEST = "/addNest";
    public static final String UPDATE_NEST = "/updateNest";
    public static final String GET_NEST_BY_ID = "/getNestByID";
    public static final String GET_ALL_NEST = "/getAllNest";

    // Bird Function
    public static final String ADD_BIRD = "/addBird";
    public static final String UPDATE_BIRD = "/updateBird";
    public static final String GET_BIRD_BY_ID = "/getBirdByID";
    public static final String GET_ALL_BIRD = "/getAllBird";
}
