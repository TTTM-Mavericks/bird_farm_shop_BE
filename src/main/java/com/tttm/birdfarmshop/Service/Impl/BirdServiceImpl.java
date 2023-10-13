package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.DTO.BirdDTO;
import com.tttm.birdfarmshop.Enums.BirdMatchingStatus;
import com.tttm.birdfarmshop.Enums.ProductStatus;
import com.tttm.birdfarmshop.Models.Bird;
import com.tttm.birdfarmshop.Models.HealthcareProfessional;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Models.TypeOfBird;
import com.tttm.birdfarmshop.Repository.BirdRepository;
import com.tttm.birdfarmshop.Repository.HealthcareProfessionalRepository;
import com.tttm.birdfarmshop.Repository.ProductRepository;
import com.tttm.birdfarmshop.Repository.TypeOfBirdRepository;
import com.tttm.birdfarmshop.Service.BirdService;
import com.tttm.birdfarmshop.Utils.Request.BirdRequest;
import com.tttm.birdfarmshop.Utils.Response.BirdMatchingResponse;
import com.tttm.birdfarmshop.Utils.Response.BirdResponse;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private boolean checkBirdInfo(BirdRequest bird){
        if(bird.getBreedingTimes() >= 5){
            return false;
        }
        if(bird.getAge() >= 4){
            return false;
        }
        return true;
    }

    private float simulateMatching(long firstNum, long secondNum){
        return (float)((firstNum + secondNum)/100)%100;
    }

    private long birdToNum(BirdRequest bird){
        try {
            // Tạo một đối tượng MessageDigest với thuật toán MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Băm chuỗi thành một mảng byte
            byte[] byteData = md.digest(bird.toString().getBytes());

            // Chuyển mảng byte thành một số dương
            long hashValue = bytesToLong(byteData) % 100000;
            return hashValue;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private long bytesToLong(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < bytes.length; i++) {
            result = (result << 8) | (bytes[i] & 0xFF);
        }
        return Math.abs(result); // Đảm bảo kết quả là số dương
    }

    private String getSizeOfBird(long num){
        if(num <= 33333){
            return "S";
        }
        if(num <= 66666){
            return "M";
        }
        return "L";
    }

    private BirdMatchingResponse caculateResult(BirdRequest firstBird, BirdRequest seconBird){
        BirdMatchingResponse response = new BirdMatchingResponse();
        if(birdToNum(firstBird) >= birdToNum(seconBird)){
            response.setColor(firstBird.getColor());
            response.setSize(getSizeOfBird(birdToNum(firstBird)));
        } else {
            response.setColor(seconBird.getColor());
            response.setSize(getSizeOfBird(birdToNum(seconBird)));
        }
        return response;
    }

    @Override
    public MessageResponse matchingBird(BirdRequest firstBird, BirdRequest secondBird) {
        if(!checkBirdInfo(firstBird)){
            return new MessageResponse("The Bird: " + firstBird.toString() + " is not eligible for pairing");
        }
        if(!checkBirdInfo(secondBird)){
            return new MessageResponse("The Bird: " + secondBird.toString() + " is not eligible for pairing");
        }
        if(!firstBird.getTypeOfBird().equals(secondBird.getTypeOfBird())){
            return new MessageResponse("This pare can not matching. Different type.");
        }
        if(firstBird.getGender() == secondBird.getGender()){
            return new MessageResponse("This pare can not matching. The same gender.");
        }
//        System.out.println(birdToNum(firstBird));
//        System.out.println(birdToNum(secondBird));
        float simulate = simulateMatching(birdToNum(firstBird),birdToNum(secondBird));
        if(simulate < 50f){
            return new MessageResponse("The success rate is not good: " + simulate);
        }
        BirdMatchingResponse expectedResult = caculateResult(firstBird, secondBird);
        expectedResult.setSuccessRate(simulate);
        expectedResult.setStatus(BirdMatchingStatus.OK);
        return new MessageResponse(expectedResult.toString());
    }

    private List<BirdRequest> getBirdList(){
        List<Bird> productList = birdRepository.findAll();
        List<BirdRequest> birdRequestList = null;
        for (Bird bird : productList) {
            if(birdRequestList == null){
                birdRequestList = new ArrayList<>();
            }
            birdRequestList.add(new BirdRequest(bird.getProduct().getProductName(),
                    bird.getTypeOfBird().toString(),
                    bird.getProduct().getDescription(),
                    bird.getAge(),
                    bird.getGender(),
                    bird.getBreedingTimes(),
                    bird.getColor(),
                    bird.getProduct().getImages()));
        }
        return birdRequestList;
    }

    @Override
    public MessageResponse matchingBirdDifferentOwner(BirdRequest firstBird) {
        if(!checkBirdInfo(firstBird)){
            return new MessageResponse("The Bird: " + firstBird.toString() + " is not eligible for pairing");
        }
        List<BirdRequest> birdlist = getBirdList();
        List<BirdMatchingResponse> responseList = null;

        for (BirdRequest secondBird: birdlist ) {
            if(!checkBirdInfo(secondBird)){
                continue;
            }
            if(!firstBird.getTypeOfBird().equals(secondBird.getTypeOfBird())){
                continue;
            }
            if(firstBird.getGender() == secondBird.getGender()){
                continue;
            }
            float simulate = simulateMatching(birdToNum(firstBird),birdToNum(secondBird));
            if(simulate < 50f){
                continue;
            }
            BirdMatchingResponse expectedResult = caculateResult(firstBird, secondBird);
            expectedResult.setSuccessRate(simulate);
            expectedResult.setStatus(BirdMatchingStatus.OK);
            if(responseList == null){
                responseList = new ArrayList<>();
                responseList.add(expectedResult);
            }
        }
        return new MessageResponse(responseList.toString());
    }
}
