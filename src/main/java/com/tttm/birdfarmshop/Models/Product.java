package com.tttm.birdfarmshop.Models;

import com.tttm.birdfarmshop.Enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "[Product]")
public class Product {
  @Id
  @Column(name = "productID", unique = true, nullable = false, columnDefinition = "NVARCHAR(255)")
  private String productID;

  @Column(name = "productName", unique = false, nullable = false, length = 100, columnDefinition = "NVARCHAR(255)")
  private String productName;

  @Column(name = "price", unique = false, nullable = false)
  private Double price;

  @Column(name = "description", unique = false, nullable = true, length = 500, columnDefinition = "NVARCHAR(255)")
  private String description;

  @Column(name = "typeOfProduct", unique = false, nullable = false, length = 100, columnDefinition = "NVARCHAR(255)")
  private String typeOfProduct;

  @Column(name = "feedback", unique = false, nullable = true, columnDefinition = "NVARCHAR(255)")
  private String feedback;

  @Column(name = "rating", unique = false, nullable = false)
  private Integer rating;

  @Column(name = "productStatus", unique = false, nullable = false)
  private ProductStatus productStatus;

  @Column(name = "quantity", unique = false, nullable = false)
  private Integer quantity;

  @OneToMany(mappedBy = "imageProduct")
  private List<Image> listImages;
}
