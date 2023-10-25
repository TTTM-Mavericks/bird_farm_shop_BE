package com.tttm.birdfarmshop.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
//@Table(name = "[Invoice]")
public class Invoice {
//  @Id
//  @Column(name = "invoiceID", unique = true, nullable = false)
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Integer invoiceID;
//
//  @Column(name = "status", nullable = false, unique = false)
//  private Boolean status;
//
//  @Column(name = "date", nullable = false, unique = false)
//  private Date date;
//
//  @OneToOne
//  @JoinColumn(name = "paymentID")
//  private PayOS payment;
  private String userID;
  private String customerPhone;
  private String customerEmail;
  private String customerAddress;
  private List<Product> productList;
  private Integer price;
  private List<Voucher> voucherList;
  private Integer totalPrice;
  private LocalDateTime orderDate;
  private Shipper shipper;
}
