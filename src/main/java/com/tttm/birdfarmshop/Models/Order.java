package com.tttm.birdfarmshop.Models;

import com.tttm.birdfarmshop.Enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "[Order]")
public class Order {
  @Id
  @Column(name = "orderID", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer orderID;

  @Column(name = "customerPhone", nullable = false, unique = false)
  private String customerPhone;

  @Column(name = "customerName", unique = false, nullable = false)
  private String customerName;

  @Column(name = "customerEmail", unique = false, nullable = false)
  private String customerEmail;

  @Column(name = "customerAddress", nullable = false, unique = false)
  private String customerAddress;

  @Column(name = "note", unique = false, nullable = true, length = 500)
  private String note;

  @Column(name = "status", nullable = false, unique = false)
  private OrderStatus status;

  @Column(name = "amount", nullable = false, unique = false)
  private Float amount;

  @Column(name = "orderDate", nullable = true, unique = false)
  @Temporal(TemporalType.DATE)
  private Date orderDate;

  @ManyToOne
  @JoinColumn(name = "customerID")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "shipperID")
  private Shipper shipper;

  public Order(String customerPhone, String customerName, String customerEmail, String customerAddress, String note, OrderStatus status, Float amount, Date orderDate, Customer customer, Shipper shipper) {
    this.customerPhone = customerPhone;
    this.customerName = customerName;
    this.customerEmail = customerEmail;
    this.customerAddress = customerAddress;
    this.note = note;
    this.status = status;
    this.amount = amount;
    this.orderDate = orderDate;
    this.customer = customer;
    this.shipper = shipper;
  }
}
