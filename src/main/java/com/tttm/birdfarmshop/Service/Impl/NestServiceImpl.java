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
                    ProductStatus.AVAILABLE,
                    dto.getQuantity()
            ));

            nestRepository.save(new Nest(NestID));
            return new MessageResponse("Success");
        }
        return new MessageResponse("Fail");
    }

    @Override
    public MessageResponse UpdateNest(String NestID, NestDTO dto) {
        try {
            Optional<Product> productOptional = productRepository.findById(NestID);
            if(productOptional.isEmpty() || !isValidFood(dto))
            {
                return new MessageResponse("Fail");
            }
            return new MessageResponse(
                    Optional
                            .ofNullable(productRepository.findById(NestID).get())
                            .map(nest ->{
                                nest.setProductName(dto.getProductName());
                                nest.setPrice(dto.getPrice());
                                nest.setDescription(dto.getDescription());
                                nest.setTypeOfProduct(dto.getTypeOfProduct());
                                nest.setImages(dto.getImages());
                                nest.setFeedback(dto.getFeedback());
                                nest.setRating(dto.getRating());
                                nest.setQuantity(dto.getQuantity());
                                productRepository.save(nest);
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
    public Product findNestByNestID(String NestID) {
        try
        {
            Optional<Product> nestOptional = productRepository.findById(NestID);
            if(nestOptional.isPresent())
            {
                return nestOptional.get();
            }
            else return new Product();
        }
        catch (Exception e)
        {
            return new Product();
        }
    }

    @Override
    public List<Product> findAllNest() {
        return productRepository.findAllNest();
    }
}
