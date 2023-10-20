package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.DTO.BirdDTO;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Utils.Request.BirdRequest;
import com.tttm.birdfarmshop.Utils.Response.BirdResponse;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;

import java.util.List;

public interface BirdService {
    MessageResponse AddNewBird(BirdDTO dto);

    MessageResponse UpdateBird(String BirdID, BirdDTO dto);

    BirdResponse findBirdByBirdID(String BirdID);
    List<BirdResponse> findAllBird();
    MessageResponse matchingBird(BirdRequest firstBird, BirdRequest secondBird);
    List<BirdResponse> matchingBirdDifferentOwner (BirdRequest birdRequest);
    List<BirdResponse> matchingBirdInShop (String id);
}
