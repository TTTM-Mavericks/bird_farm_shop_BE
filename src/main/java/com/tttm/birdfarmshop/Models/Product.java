package com.tttm.birdfarmshop.Models;

import com.tttm.birdfarmshop.Enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[Product]")
public class Product {
  @Id
  @Column(name = "productID", unique = true, nullable = false)
  private String productID;

  @Column(name = "productName", unique = false, nullable = false, length = 100)
  private String productName;

  @Column(name = "price", unique = false, nullable = false)
  private Double price;

  @Column(name = "description", unique = false, nullable = true, length = 500)
  private String description;

  @Column(name = "typeOfProduct", unique = false, nullable = false, length = 100)
  private String typeOfProduct;

  @Column(name = "images", unique = false, nullable = true)
  private String images;

  @Column(name = "feedback", unique = false, nullable = true)
  private String feedback;

  @Column(name = "rating", unique = false, nullable = false)
  private Integer rating;

  @Column(name = "productStatus", unique = false, nullable = false)
  private ProductStatus productStatus;
}
