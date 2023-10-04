package com.tttm.birdfarmshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NestDTO {
    private String productName;

    private Double price;

    private String description;

    private String typeOfProduct;

    private String images;

    private String feedback;

    private Integer rating;
}
