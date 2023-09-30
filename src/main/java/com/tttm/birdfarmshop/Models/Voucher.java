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
@Table(name = "[Voucher]")
public class Voucher {
  @Id
  @Column(name = "voucherID", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer voucherID;

  @Column(name = "voucherName", nullable = false, unique = true, length = 100)
  private Integer voucherName;

  @Column(name = "startDate", nullable = false, unique = false)
  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Column(name = "endDate", nullable = false, unique = false)
  @Temporal(TemporalType.DATE)
  private Date endDate;

  @Column(name = "value", nullable = false, unique = false)
  private Integer value;

  @ManyToOne
  @JoinColumn(name = "sellerID")
  private Seller seller;
}
