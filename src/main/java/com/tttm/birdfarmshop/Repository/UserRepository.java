package com.tttm.birdfarmshop.Repository;

import com.tttm.birdfarmshop.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(
            value = "select * from user where email = ?1", nativeQuery = true
    )
    User findUserByEmail(String email);

    @Query(
            value = " select * from user", nativeQuery = true
    )
    List<User> getAllUsers();

    @Query(
            value = "select * from user where phone = ?1", nativeQuery = true
    )
    User findUserByPhone(String phone);

    @Query(
            value = "select * from user where email = ?1 and account_status = ?2", nativeQuery = true
    )
    User findUserByEmailAndActiveStatus(String email, boolean accountStatus);

    @Query(
            value = " select * from user where role = ?1", nativeQuery = true
    )
    List<User> getAllUsersBasedOnRole(String role);
}
