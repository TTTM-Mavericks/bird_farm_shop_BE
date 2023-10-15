package com.tttm.birdfarmshop.Controller;

import com.tttm.birdfarmshop.Constant.ConstantAPI;
import com.tttm.birdfarmshop.Constant.ConstantParametter;
import com.tttm.birdfarmshop.DTO.FoodDTO;
import com.tttm.birdfarmshop.DTO.NestDTO;
import com.tttm.birdfarmshop.Exception.CustomException;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Service.FoodService;
import com.tttm.birdfarmshop.Service.NestService;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConstantAPI.NEST)
public class NestController {
    private final NestService nestService;
    @PostMapping(ConstantAPI.ADD_NEST)
    public ResponseEntity<MessageResponse> addNest(@RequestBody NestDTO dto) throws CustomException {
        try {
            return new ResponseEntity<>(nestService.AddNewNest(dto), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping(ConstantAPI.UPDATE_NEST + ConstantParametter.NEST_ID)
    public ResponseEntity<MessageResponse> updateNest(@PathVariable ("NestID") String NestID,
                                                      @RequestBody NestDTO dto) throws CustomException {
        try {
            return new ResponseEntity<>(nestService.UpdateNest(NestID, dto), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping(ConstantAPI.GET_NEST_BY_ID + ConstantParametter.NEST_ID)
    public ResponseEntity<Product> getNestByID(@PathVariable ("NestID") String NestID) throws CustomException {
        try {
            return new ResponseEntity<>(nestService.findNestByNestID(NestID), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(ConstantAPI.GET_ALL_NEST)
    public ResponseEntity<List<Product>> getAllNest() throws CustomException {
        try {
            return new ResponseEntity<>(nestService.findAllNest(), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
