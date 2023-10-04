package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.DTO.FoodDTO;
import com.tttm.birdfarmshop.DTO.NestDTO;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;

import java.util.List;

public interface NestService {
    MessageResponse AddNewNest(NestDTO dto);

    MessageResponse UpdateNest(String NestID, NestDTO dto);

    Product findNestByNestID(String NestID);

    List<Product> findAllNest();
}
