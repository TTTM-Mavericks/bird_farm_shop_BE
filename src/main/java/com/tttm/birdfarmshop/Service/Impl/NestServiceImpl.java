package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.DTO.FoodDTO;
import com.tttm.birdfarmshop.DTO.NestDTO;
import com.tttm.birdfarmshop.Enums.ProductStatus;
import com.tttm.birdfarmshop.Models.Nest;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Repository.NestRepository;
import com.tttm.birdfarmshop.Repository.ProductRepository;
import com.tttm.birdfarmshop.Service.NestService;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NestServiceImpl implements NestService {
    private final NestRepository nestRepository;
    private final ProductRepository productRepository;
    private boolean isValidFood(NestDTO dto)
    {
        return !dto.getProductName().isBlank() && !dto.getProductName().isEmpty() && dto.getPrice() >= 0
                && !dto.getTypeOfProduct().isEmpty() && !dto.getTypeOfProduct().isBlank() && dto.getRating() >= 0;
    }
    @Override
    public MessageResponse AddNewNest(NestDTO dto) {
        int size = (int) nestRepository.findAll().stream().count();
        String NestID = "N00" + (size + 1);

        if(isValidFood(dto))
        {
            productRepository.save(new Product(
                    NestID,
                    dto.getProductName(),
                    dto.getPrice(),
                    dto.getDescription(),
                    dto.getTypeOfProduct(),
                    dto.getImages(),
                    dto.getFeedback(),
                    dto.getRating(),
                    ProductStatus.AVAILABLE
            ));

            nestRepository.save(new Nest(NestID));
            return new MessageResponse("Add new Nest Successfully");
        }
        return new MessageResponse("Fail to Add new Nest");
    }

    @Override
    public MessageResponse UpdateNest(String NestID, NestDTO dto) {
        if(productRepository.findById(NestID).get() == null)
        {
            return new MessageResponse("NestID is not existed");
        }
        if(isValidFood(dto))
        {
            return new MessageResponse(
                    Optional
                            .ofNullable(productRepository.findById(NestID).get())
                            .map(food ->{
                                food.setProductName(dto.getProductName());
                                food.setPrice(dto.getPrice());
                                food.setDescription(dto.getDescription());
                                food.setTypeOfProduct(dto.getTypeOfProduct());
                                food.setImages(dto.getImages());
                                food.setFeedback(dto.getFeedback());
                                food.setRating(dto.getRating());
                                productRepository.save(food);
                                return "Update Nest Successfully";
                            })
                            .orElse("Fail to update Nest")
            );
        }
        else return new MessageResponse("Invalid type of input. Fail to update Type Of Nest");
    }

    @Override
    public Product findNestByNestID(String NestID) {
        return productRepository.findById(NestID)
                .orElseThrow(() -> new NotFoundException("Nest not Found with ID: " + NestID));
    }

    @Override
    public List<Product> findAllNest() {
        return productRepository.findAllNest();
    }
}
