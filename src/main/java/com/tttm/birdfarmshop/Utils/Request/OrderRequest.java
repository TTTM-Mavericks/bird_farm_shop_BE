package com.tttm.birdfarmshop.Utils.Request;

import com.tttm.birdfarmshop.Models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer CustomerID;

    private String customerPhone;

    private String customerName;

    private String customerEmail;

    private String customerAddress;

    private String note;

    private List<String> listProduct;  // Store List of Product ID
}
