package com.tttm.birdfarmshop.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.birdfarmshop.Constant.ConstantAPI;
import com.tttm.birdfarmshop.Constant.ConstantParametter;
import com.tttm.birdfarmshop.Exception.CustomException;
import com.tttm.birdfarmshop.Service.OrderService;
import com.tttm.birdfarmshop.Utils.Request.OrderRequest;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import com.tttm.birdfarmshop.Utils.Response.OrderResponse;
import jakarta.websocket.server.PathParam;
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
    public ObjectNode createOrder(@RequestBody OrderRequest dto) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return orderService.CreateOrder(dto);
        } catch (Exception ex) {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", ex.getLocalizedMessage());
            respon.put("message", ex.getMessage());
            return respon;
        }
    }

    @GetMapping(ConstantAPI.UPDATE_PAYMENT_STATUS + ConstantParametter.ORDER_ID)
    public ObjectNode createOrder(@PathVariable("OrderID") Integer orderID) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return orderService.updatePaymentForOrder(orderID);
        } catch (Exception ex) {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", ex.getLocalizedMessage());
            respon.put("message", ex.getMessage());
            return respon;
        }
    }

    @GetMapping(ConstantAPI.GET_ORDER_BY_ID + ConstantParametter.ORDER_ID)
    public ResponseEntity<OrderResponse> getFoodByID(@PathVariable("OrderID") Integer OrderID) throws CustomException {
        try {
            return new ResponseEntity<>(orderService.getOrderByOrderID(OrderID), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(ConstantAPI.GET_ALL_ORDER)
    public ResponseEntity<List<OrderResponse>> getAllOrder() throws CustomException {
        try {
            return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(ConstantAPI.DELETE_ORDER + ConstantParametter.ORDER_ID)
    public ResponseEntity<MessageResponse> deleteOrder(@PathVariable("OrderID") Integer OrderID) throws CustomException {
        try {
            return new ResponseEntity<>(orderService.DeleteOrder(OrderID), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
