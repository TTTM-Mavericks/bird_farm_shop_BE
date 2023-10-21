package com.tttm.birdfarmshop.Utils.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BirdResponse {
    private String productID;

    private String productName;

    private Double price;

    private String description;

    private String typeOfProduct;

    private List<String> images;

    private String feedback;

    private Integer rating;

    private Integer quantity;

    private Integer age;

    private Boolean gender;

    private Boolean fertility;

    private String typeOfBirdID;

    private Integer healthcareProfessionalID;
}
