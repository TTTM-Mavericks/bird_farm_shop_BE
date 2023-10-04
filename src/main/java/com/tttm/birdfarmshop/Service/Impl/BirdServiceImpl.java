package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.DTO.BirdDTO;
import com.tttm.birdfarmshop.Enums.ProductStatus;
import com.tttm.birdfarmshop.Models.*;
import com.tttm.birdfarmshop.Repository.*;
import com.tttm.birdfarmshop.Service.BirdService;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BirdServiceImpl implements BirdService {
    private final BirdRepository birdRepository;
    private final ProductRepository productRepository;
    private final HealthcareProfessionalRepository healthcareProfessionalRepository;
    private final TypeOfBirdRepository typeOfBirdRepository;
    private final Logger logger = LoggerFactory.getLogger(BirdServiceImpl.class);
    private boolean isValidFood(BirdDTO dto)
    {
        return !dto.getProductName().isBlank() && !dto.getProductName().isEmpty() && dto.getPrice() >= 0
                && !dto.getTypeOfProduct().isEmpty() && !dto.getTypeOfProduct().isBlank() && dto.getRating() >= 0
                && !dto.getFertility().toString().isBlank()  && !dto.getFertility().toString().isEmpty()
                && !dto.getTypeOfBirdID().isEmpty() && !dto.getTypeOfBirdID().isBlank();
    }
    @Override
    public MessageResponse AddNewBird(BirdDTO dto) {
        int size = (int) birdRepository.findAll().stream().count();
        String BirdID = "B00" + (size + 1);

        TypeOfBird typeOfBird = typeOfBirdRepository.findById(dto.getTypeOfBirdID()).get();
        if(typeOfBird == null)
        {
            return new MessageResponse("Type Of Bird ID can found in DB");
        }

        HealthcareProfessional healthcareProfessional = healthcareProfessionalRepository.findById(dto.getHealthcareProfessionalID()).get();
        if(healthcareProfessional == null)
        {
            return new MessageResponse("HealthcareProfessional ID can found in DB");
        }

        if(isValidFood(dto))
        {
            productRepository.save(new Product(
                    BirdID,
                    dto.getProductName(),
                    dto.getPrice(),
                    dto.getDescription(),
                    dto.getTypeOfProduct(),
                    dto.getImages(),
                    dto.getFeedback(),
                    dto.getRating(),
                    ProductStatus.AVAILABLE
            ));

            birdRepository.save(new Bird(
                    BirdID,
                    dto.getAge(),
                    dto.getGender(),
                    false,
                    dto.getFertility(),
                    typeOfBird,
                    healthcareProfessional
            ));
            return new MessageResponse("Add new Bird Successfully");
        }
        return new MessageResponse("Fail to Add new Bird");
    }

    @Override
    public MessageResponse UpdateFood(String BirdID, BirdDTO dto) {
        if(birdRepository.findById(BirdID).get() == null)
        {
            return new MessageResponse("BirdID is not existed");
        }

        TypeOfBird typeOfBird = typeOfBirdRepository.findById(dto.getTypeOfBirdID()).get();
        if(typeOfBird == null)
        {
            return new MessageResponse("Type Of Bird ID can found in DB");
        }

        HealthcareProfessional healthcareProfessional = healthcareProfessionalRepository.findById(dto.getHealthcareProfessionalID()).get();
        if(healthcareProfessional == null)
        {
            return new MessageResponse("HealthcareProfessional ID can found in DB");
        }

        if(isValidFood(dto)) {

            Product product = productRepository.findById(BirdID).get();
            product.setProductName(dto.getProductName());
            product.setPrice(dto.getPrice());
            product.setDescription(dto.getDescription());
            product.setTypeOfProduct(dto.getTypeOfProduct());
            product.setImages(dto.getImages());
            product.setFeedback(dto.getFeedback());
            product.setRating(dto.getRating());
            productRepository.save(product);

            Bird bird = birdRepository.findById(BirdID).get();
            bird.setAge(dto.getAge());
            bird.setGender(dto.getGender());
            bird.setFertility(dto.getFertility());
            bird.setTypeOfBird(typeOfBird);
            bird.setHealthcareProfessional(healthcareProfessional);
            birdRepository.save(bird);

            return new MessageResponse("Update Bird Successfully");
        }
        else return new MessageResponse("Invalid type of input. Fail to update Bird");
    }

    @Override
    public Product findBirdByBirdID(String BirdID) {
        return productRepository.findById(BirdID)
                .orElseThrow(() -> new NotFoundException("Bird Not Found With ID: " + BirdID));
    }

    @Override
    public List<Product> findAllBird() {
        return productRepository.findAllBird();
    }
}
