package com.tttm.birdfarmshop.Models;

import jakarta.persistence.*;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[Invoice]")
public class Invoice {
  @Id
  @Column(name = "invoiceID", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer invoiceID;

  @Column(name = "status", nullable = false, unique = false)
  private Boolean status;

  @Column(name = "date", nullable = false, unique = false)
  private Date date;

  @OneToOne
  @JoinColumn(name = "paymentID")
  private Payment payment;
}
