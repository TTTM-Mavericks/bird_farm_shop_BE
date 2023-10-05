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
                    ProductStatus.AVAILABLE,
                    dto.getQuantity()
            ));

            foodRepository.save(new Food(FoodID));
            return new MessageResponse("Success");
        }
        return new MessageResponse("Fail");
    }

    @Override
    public MessageResponse UpdateFood(String productID, FoodDTO dto) {
        try {
            Optional<Product> productOptional = productRepository.findById(productID);
            if(productOptional.isEmpty() || !isValidFood(dto))
            {
                return new MessageResponse("Fail");
            }
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
                                food.setQuantity(dto.getQuantity());
                                productRepository.save(food);
                                return "Success";
                            })
                            .orElse("Fail")
                );
        }
        catch (Exception ex)
        {
            return new MessageResponse("Fail");
        }
    }

    @Override
    public Product findFoodByFoodID(String foodID) {
        try
        {
            Optional<Product> foodOptional = productRepository.findById(foodID);
            if(foodOptional.isPresent())
            {
                return foodOptional.get();
            }
            else return new Product();
        }
        catch (Exception e)
        {
            return new Product();
        }
    }

    @Override
    public List<Product> findAllFood() {
        return productRepository.findAllFood();
    }
}
