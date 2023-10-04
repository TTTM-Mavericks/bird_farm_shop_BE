package com.tttm.birdfarmshop.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[Bird]")
public class Bird {
  @Id
  @Column(name = "birdID", nullable = false, unique = true)
  private String birdID;

  @Column(name = "age", nullable = true, unique = false)
  private Integer age;

  @Column(name = "gender", nullable = true, unique = false)
  private Boolean gender;

  @Column(name = "status", nullable = false, unique = false)
  private Boolean status;

  @Column(name = "fertility", nullable = false, unique = false)
  private Boolean fertility;

  @OneToOne
  @JoinColumn(name = "birdID", referencedColumnName = "productID", insertable = false, updatable = false)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "typeID")
  private TypeOfBird typeOfBird;

  @ManyToOne
  @JoinColumn(name = "healthcareID")
  private HealthcareProfessional healthcareProfessional;

  public Bird(String birdID, Integer age, Boolean gender, Boolean status, Boolean fertility, TypeOfBird typeOfBird, HealthcareProfessional healthcareProfessional) {
    this.birdID = birdID;
    this.age = age;
    this.gender = gender;
    this.status = status;
    this.fertility = fertility;
    this.typeOfBird = typeOfBird;
    this.healthcareProfessional = healthcareProfessional;
  }

  public Bird(String birdID, Integer age, Boolean gender, Boolean status, Boolean fertility) {
    this.birdID = birdID;
    this.age = age;
    this.gender = gender;
    this.status = status;
    this.fertility = fertility;
  }
}
