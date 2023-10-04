package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.DTO.BirdDTO;
import com.tttm.birdfarmshop.DTO.FoodDTO;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;

import java.util.List;

public interface BirdService {
    MessageResponse AddNewBird(BirdDTO dto);

    MessageResponse UpdateFood(String BirdID, BirdDTO dto);

    Product findBirdByBirdID(String BirdID);

    List<Product> findAllBird();
}
