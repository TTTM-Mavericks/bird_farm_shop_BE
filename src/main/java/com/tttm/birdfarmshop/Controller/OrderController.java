package com.tttm.birdfarmshop.Controller;

import com.tttm.birdfarmshop.Constant.ConstantAPI;
import com.tttm.birdfarmshop.Constant.ConstantParametter;
import com.tttm.birdfarmshop.Exception.CustomException;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Service.OrderService;
import com.tttm.birdfarmshop.Utils.Request.OrderRequest;
import com.tttm.birdfarmshop.Utils.Response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConstantAPI.ORDER)
public class OrderController {
    private final OrderService orderService;
    @PostMapping(ConstantAPI.CREATE_ORDER)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest dto) throws CustomException {
        try {
            return new ResponseEntity<>(orderService.createOrder(dto), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(ConstantAPI.GET_ORDER_BY_ID + ConstantParametter.ORDER_ID)
    public ResponseEntity<OrderResponse> getFoodByID(@PathVariable ("OrderID") Integer OrderID) throws CustomException {
        try {
            return new ResponseEntity<>(orderService.getOrderByOrderID(OrderID), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(ConstantAPI.GET_ALL_ORDER)
    public ResponseEntity<List<OrderResponse>> getAllOrder() throws CustomException {
        try {
            return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
