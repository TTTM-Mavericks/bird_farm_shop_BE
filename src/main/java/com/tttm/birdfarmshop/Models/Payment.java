package com.tttm.birdfarmshop.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[Payment]")
public class Payment {
  @Id
  @Column(name = "paymentID", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer paymentID;

  @Column(name = "typeOfPayment", nullable = true, unique = false)
  private PaymentType tyeOfPayment;

  @OneToOne
  @JoinColumn(name = "orderID")
  private Order order;
}
