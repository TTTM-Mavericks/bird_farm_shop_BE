package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.DTO.FoodDTO;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import com.tttm.birdfarmshop.Utils.Response.ProductResponse;

import java.util.List;

public interface FoodService {
    MessageResponse AddNewFood(FoodDTO dto);

    MessageResponse UpdateFood(String productID, FoodDTO dto);

    ProductResponse findFoodByFoodID(String foodID);

    List<ProductResponse> findAllFood();
}
