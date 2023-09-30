package com.tttm.birdfarmshop.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[User]")
public class User implements UserDetails {
  @Id
  @Column(name = "userID", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userID;

  @Column(name = "firstName", nullable = true, unique = false, length = 50)
  private String firstName;

  @Column(name = "lastName", nullable = true, unique = false, length = 50)
  private String lastName;

  @Column(name = "email", nullable = false, unique = true, length = 100)
  private String email;

  @Column(name = "phone", nullable = false, unique = true, length = 12)
  private String phone;

  @Column(name = "password", nullable = false, unique = false, length = 500)
  private String password;

  @Column(name = "gender", nullable = true, unique = false)
  private Boolean gender;

  @Column(name = "dateOfBirth", nullable = true, unique = false)
  @Temporal(TemporalType.DATE)
  private Date dateOfBirth;

  @Column(name = "address", nullable = false, unique = false, length = 200)
  private String address;

  @Column(name = "accountStatus", nullable = false, unique = false)
  private Boolean accountStatus;

  public User(String firstName, String lastName, String email, String phone, String password, Boolean gender, Date dateOfBirth, String address, Boolean accountStatus, ERole role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
    this.accountStatus = accountStatus;
    this.role = role;
  }

  public User(String firstName) {
    this.firstName = firstName;
  }

  //  @OneToOne
//  @JoinColumn(name = "roleID", referencedColumnName = "roleID", insertable = false, updatable = false)
//  private Role role;

  @Enumerated(EnumType.STRING)
  private ERole role;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  public String getUsername() {
    return email;
  }

  public boolean isAccountNonExpired() {
    return true;
  }

  public boolean isAccountNonLocked() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isEnabled() {
    return true;
  }


}
