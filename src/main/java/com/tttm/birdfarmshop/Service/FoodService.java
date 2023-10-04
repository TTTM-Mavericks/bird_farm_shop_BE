package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.DTO.FoodDTO;
import com.tttm.birdfarmshop.Models.Food;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;

import java.util.List;

public interface FoodService {
    MessageResponse AddNewFood(FoodDTO dto);

    MessageResponse UpdateFood(String productID, FoodDTO dto);

    Product findFoodByFoodID(String foodID);

    List<Product> findAllFood();
}
