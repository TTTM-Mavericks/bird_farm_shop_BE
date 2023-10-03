package com.tttm.birdfarmshop.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[TypeOfBird]")
public class TypeOfBird {

  @Id
  @Column(name = "typeID", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer typeID;

  @Column(name = "typeName", nullable = false, unique = false, length = 100)
  private String typeName;

  @Column(name = "quantity", unique = false, nullable = false)
  private Integer quantity;

  @OneToMany(mappedBy = "typeOfBird")
  private List<Bird> birdList;

  public TypeOfBird(String typeName, Integer quantity) {
    this.typeName = typeName;
    this.quantity = quantity;
  }
}
