package com.tttm.birdfarmshop.Controller;

import com.tttm.birdfarmshop.Constant.ConstantAPI;
import com.tttm.birdfarmshop.Constant.ConstantParametter;
import com.tttm.birdfarmshop.DTO.FoodDTO;
import com.tttm.birdfarmshop.Exception.CustomException;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Service.FoodService;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConstantAPI.FOOD)
public class FoodController {
    private final FoodService foodService;
    @PostMapping(ConstantAPI.ADD_FOOD)
    public ResponseEntity<MessageResponse> addFood(@RequestBody FoodDTO dto) throws CustomException {
        try {
            return new ResponseEntity<>(foodService.AddNewFood(dto), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping(ConstantAPI.UPDATE_FOOD + ConstantParametter.FOOD_ID)
    public ResponseEntity<MessageResponse> updateFood(@PathVariable ("FoodID") String FoodID,
                                                            @RequestBody FoodDTO dto) throws CustomException {
        try {
            return new ResponseEntity<>(foodService.UpdateFood(FoodID, dto), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping(ConstantAPI.GET_FOOD_BY_ID + ConstantParametter.FOOD_ID)
    public ResponseEntity<Product> getFoodByID(@PathVariable ("FoodID") String FoodID) throws CustomException {
        try {
            return new ResponseEntity<>(foodService.findFoodByFoodID(FoodID), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(ConstantAPI.GET_ALL_FOOD)
    public ResponseEntity<List<Product>> getAllFood() throws CustomException {
        try {
            return new ResponseEntity<>(foodService.findAllFood(), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
