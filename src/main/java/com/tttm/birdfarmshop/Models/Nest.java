package com.tttm.birdfarmshop.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[Nest]")
public class Nest {
    @Id
    @Column(name = "nestID", unique = true, nullable = false)
    private Integer nestID;

    @OneToOne
    @JoinColumn(name = "nestID", referencedColumnName = "productID")
    private Product product;

}
