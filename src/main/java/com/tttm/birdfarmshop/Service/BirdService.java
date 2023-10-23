package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.DTO.BirdDTO;
import com.tttm.birdfarmshop.Exception.CustomException;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Utils.Request.BirdRequest;
import com.tttm.birdfarmshop.Utils.Request.FilterProduct;
import com.tttm.birdfarmshop.Utils.Response.BirdResponse;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import com.tttm.birdfarmshop.Utils.Response.ProductResponse;

import java.util.List;

public interface BirdService {
    MessageResponse AddNewBird(BirdDTO dto);
    MessageResponse UpdateBird(String BirdID, BirdDTO dto);
    BirdResponse findBirdByBirdID(String BirdID);
    List<BirdResponse> findAllBird();
//    List<Product> findAllBird();
    BirdResponse matchingBird(BirdRequest firstBird, BirdRequest secondBird) throws CustomException;
    List<BirdResponse> matchingBirdDifferentOwner (BirdRequest birdRequest) throws CustomException;
    List<BirdResponse> matchingBirdInShop (String id);
    List<BirdResponse> findBirdByName(String name);
    List<BirdResponse> sortBirdByPriceAscending();
    List<BirdResponse> sortBirdByPriceDescending();

    List<BirdResponse> sortBirdByProductNameAscending();
    List<BirdResponse> sortBirdByProductNameDescending();
    List<BirdResponse> filterBirdByCustomerRequest(FilterProduct filterProduct);

}
