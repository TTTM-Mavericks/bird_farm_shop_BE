package com.tttm.birdfarmshop.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[Role]")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "roleID", unique = true, nullable = false)
  private Integer roleID;

  @Column(name = "roleName")
  @Enumerated(EnumType.STRING)
  private ERole roleName;
}
