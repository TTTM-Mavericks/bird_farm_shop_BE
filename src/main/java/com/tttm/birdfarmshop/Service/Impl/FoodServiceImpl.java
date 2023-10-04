package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.DTO.FoodDTO;
import com.tttm.birdfarmshop.Enums.ProductStatus;
import com.tttm.birdfarmshop.Models.Food;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Repository.FoodRepository;
import com.tttm.birdfarmshop.Repository.ProductRepository;
import com.tttm.birdfarmshop.Service.FoodService;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final ProductRepository productRepository;
    private boolean isValidFood(FoodDTO dto)
    {
        return !dto.getProductName().isBlank() && !dto.getProductName().isEmpty() && dto.getPrice() >= 0
                && !dto.getTypeOfProduct().isEmpty() && !dto.getTypeOfProduct().isBlank() && dto.getRating() >= 0;
    }
    @Override
    public MessageResponse AddNewFood(FoodDTO dto) {

        int size = (int) foodRepository.findAll().stream().count();
        String FoodID = "F00" + (size + 1);

        if(isValidFood(dto))
        {
            productRepository.save(new Product(
                    FoodID,
                    dto.getProductName(),
                    dto.getPrice(),
                    dto.getDescription(),
                    dto.getTypeOfProduct(),
                    dto.getImages(),
                    dto.getFeedback(),
                    dto.getRating(),
                    ProductStatus.AVAILABLE
            ));

            foodRepository.save(new Food(FoodID));
            return new MessageResponse("Add new Food Successfully");
        }
        return new MessageResponse("Fail to Add new Food");
    }

    @Override
    public MessageResponse UpdateFood(String productID, FoodDTO dto) {
        if(productRepository.findById(productID).get() == null)
        {
            return new MessageResponse("FoodID is not existed");
        }
        if(isValidFood(dto))
        {
            return new MessageResponse(
                    Optional
                        .ofNullable(productRepository.findById(productID).get())
                        .map(food ->{
                            food.setProductName(dto.getProductName());
                            food.setPrice(dto.getPrice());
                            food.setDescription(dto.getDescription());
                            food.setTypeOfProduct(dto.getTypeOfProduct());
                            food.setImages(dto.getImages());
                            food.setFeedback(dto.getFeedback());
                            food.setRating(dto.getRating());
                            productRepository.save(food);
                            return "Update Food Successfully";
                        })
                        .orElse("Fail to update Food")
            );
        }
        else return new MessageResponse("Invalid type of input. Fail to update Type Of Food");
    }

    @Override
    public Product findFoodByFoodID(String foodID) {
        return productRepository.findById(foodID)
                .orElseThrow(() -> new NotFoundException("Food not Found with ID: " + foodID));
    }

    @Override
    public List<Product> findAllFood() {
        return productRepository.findAllFood();
    }
}
