package com.tttm.birdfarmshop.Controller;

import com.tttm.birdfarmshop.Constant.ConstantAPI;
import com.tttm.birdfarmshop.Constant.ConstantParametter;
import com.tttm.birdfarmshop.DTO.BirdDTO;
import com.tttm.birdfarmshop.Exception.CustomException;
import com.tttm.birdfarmshop.Service.BirdService;
import com.tttm.birdfarmshop.Utils.Request.BirdMatchingRequest;
import com.tttm.birdfarmshop.Utils.Request.BirdRequest;
import com.tttm.birdfarmshop.Utils.Response.BirdResponse;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConstantAPI.BIRD)
public class BirdController {
    private final BirdService birdService;

    @PostMapping(ConstantAPI.ADD_BIRD)
    public ResponseEntity<MessageResponse> addBird(@RequestBody BirdDTO dto) throws CustomException {
        try {
            return new ResponseEntity<>(birdService.AddNewBird(dto), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(ConstantAPI.UPDATE_BIRD + ConstantParametter.BIRD_ID)
    public ResponseEntity<MessageResponse> updateBird(@PathVariable("BirdID") String BirdID, @RequestBody BirdDTO dto) throws CustomException {
        try {
            return new ResponseEntity<>(birdService.UpdateBird(BirdID, dto), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(ConstantAPI.GET_BIRD_BY_ID + ConstantParametter.BIRD_ID)
    public ResponseEntity<BirdResponse> getBirdByID(@PathVariable ("BirdID") String BirdID) throws CustomException {
        try {
            return new ResponseEntity<>(birdService.findBirdByBirdID(BirdID), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(ConstantAPI.GET_ALL_BIRD)
    public ResponseEntity<List<BirdResponse>> getAllBird() throws CustomException {
        try {
            return new ResponseEntity<>(birdService.findAllBird(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(ConstantAPI.MATCHING_BIRD_FROM_SAME_OWNER)
    public ResponseEntity<MessageResponse> matchingSameOwner(@RequestBody BirdMatchingRequest bird)
        throws CustomException {
        try {
            return new ResponseEntity<>(birdService.matchingBird(bird.getFirstBird(), bird.getSecondBird()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(ConstantAPI.MATCHING_BIRD_FROM_DIFFERENT_OWNER)
    public ResponseEntity<MessageResponse> matchingDifferentOwner(@RequestBody BirdRequest bird)
            throws CustomException {
        try {
            return new ResponseEntity<>(birdService.matchingBirdDifferentOwner(bird), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
