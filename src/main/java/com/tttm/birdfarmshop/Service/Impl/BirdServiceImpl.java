package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.DTO.BirdDTO;
import com.tttm.birdfarmshop.Enums.ProductStatus;
import com.tttm.birdfarmshop.Models.*;
import com.tttm.birdfarmshop.Repository.*;
import com.tttm.birdfarmshop.Service.BirdService;
import com.tttm.birdfarmshop.Utils.Response.BirdResponse;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<TypeOfBird> typeOfBirdOptional = typeOfBirdRepository.findById(dto.getTypeOfBirdID());
        Optional<HealthcareProfessional> healthcareProfessionalOptional = healthcareProfessionalRepository.findById(dto.getHealthcareProfessionalID());

        if (typeOfBirdOptional.isEmpty() || healthcareProfessionalOptional.isEmpty() || !isValidFood(dto)) {
            return new MessageResponse("Fail");
        }

        int size = (int) birdRepository.count();
        String BirdID = "B00" + (size + 1);

        TypeOfBird typeOfBird = typeOfBirdOptional.get();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalOptional.get();

         productRepository.save(new Product(
                BirdID,
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

        birdRepository.save(new Bird(
                BirdID,
                dto.getAge(),
                dto.getGender(),
                false,
                dto.getFertility(),
                typeOfBird,
                healthcareProfessional
        ));

        // Update Type Of Bird Quantity
        int quantity = typeOfBird.getQuantity();
        typeOfBird.setQuantity(quantity + 1);
        typeOfBirdRepository.save(typeOfBird);

        return new MessageResponse("Success");
    }

    @Override
    public MessageResponse UpdateBird(String BirdID, BirdDTO dto) {
        try {
            Optional<Product> productOptional = productRepository.findById(BirdID);
            if (productOptional.isEmpty()) {
                return new MessageResponse("Fail");
            }

            Optional<TypeOfBird> typeOfBird = typeOfBirdRepository.findById(dto.getTypeOfBirdID());
            if (typeOfBird.isEmpty()) {
                return new MessageResponse("Fail");
            }

            Optional<HealthcareProfessional> healthcareProfessional = healthcareProfessionalRepository.findById(dto.getHealthcareProfessionalID());
            if (healthcareProfessional.isEmpty()) {
                return new MessageResponse("Fail");
            }

            if (isValidFood(dto)) {
                // Update Product Info
                Product product = productOptional.get();
                product.setProductName(dto.getProductName());
                product.setPrice(dto.getPrice());
                product.setDescription(dto.getDescription());
                product.setTypeOfProduct(dto.getTypeOfProduct());
                product.setImages(dto.getImages());
                product.setFeedback(dto.getFeedback());
                product.setRating(dto.getRating());
                product.setQuantity(dto.getQuantity());
                productRepository.save(product);

                // Update Bird Info
                Bird bird = birdRepository.findById(BirdID).orElse(null);
                if (bird == null) {
                    return new MessageResponse("Fail");
                }

                TypeOfBird oldTypeOfBird = bird.getTypeOfBird();

                bird.setAge(dto.getAge());
                bird.setGender(dto.getGender());
                bird.setFertility(dto.getFertility());
                bird.setTypeOfBird(typeOfBird.get());
                bird.setHealthcareProfessional(healthcareProfessional.get());
                birdRepository.save(bird);

                // Update Type Of Bird Quantity
                int oldQuantity = oldTypeOfBird.getQuantity();
                oldTypeOfBird.setQuantity(oldQuantity - 1);
                typeOfBirdRepository.save(oldTypeOfBird);

                int newQuantity = typeOfBird.get().getQuantity();
                typeOfBird.get().setQuantity(newQuantity + 1);
                typeOfBirdRepository.save(typeOfBird.get());

                return new MessageResponse("Success");
            } else {
                return new MessageResponse("Fail");
            }
        } catch (Exception ex) {
            return new MessageResponse("Fail");
        }
    }

    @Override
    public BirdResponse findBirdByBirdID(String birdID) {
        try
        {
            Optional<Product> productOptional = productRepository.findById(birdID);
            Optional<Bird> birdOptional = birdRepository.findById(birdID);
            if(productOptional.isPresent() && birdOptional.isPresent())
            {
                return mapperedToBirdRepsonse(productOptional.get(), birdOptional.get());
            }
            else return new BirdResponse();
        }
        catch (Exception e)
        {
            return new BirdResponse();
        }
    }

    @Override
    public List<BirdResponse> findAllBird() {
        List<BirdResponse> BirdResponseList = new ArrayList<>();
        List<Product> productList = productRepository.findAllBird();
        for(Product product : productList)
        {
            Optional<Bird> birdOptional = birdRepository.findById(product.getProductID());
            if(birdOptional.isPresent())
            {
                BirdResponseList.add(mapperedToBirdRepsonse(product, birdOptional.get()));
            }
        }
        return BirdResponseList;
    }


    private BirdResponse mapperedToBirdRepsonse(Product product, Bird bird)
    {
        return new BirdResponse(
                product.getProductID(),
                product.getProductName(),
                product.getPrice(),
                product.getDescription(),
                product.getTypeOfProduct(),
                product.getImages(),
                product.getFeedback(),
                product.getRating(),
                product.getQuantity(),
                bird.getAge(),
                bird.getGender(),
                bird.getFertility(),
                bird.getTypeOfBird().getTypeID(),
                bird.getHealthcareProfessional().getHealthcareID()
        );
    }
}
