package com.tttm.birdfarmshop.Service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.tttm.birdfarmshop.Exception.ResourceNotFoundException;
import com.tttm.birdfarmshop.Models.PayOS;
import com.tttm.birdfarmshop.Repository.PayOSRepository;
import com.tttm.birdfarmshop.Service.PayOSService;
import org.springframework.stereotype.Service;


@Service
public class PayOSServiceImpl implements PayOSService {

  private PayOSRepository orderRepository;

  public PayOSServiceImpl(PayOSRepository orderRepository) {
    super();
    this.orderRepository = orderRepository;
  }

  @Override
  public void CreateOrder(PayOS order) {
    orderRepository.save(order);
  }

  @Override
  public PayOS getOrder(int orderId) {
    return orderRepository.findById((long) orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));
  }

  @Override
  public void updatePaymentForOrder(int orderId, JsonNode paymentData) {
    System.out.println(paymentData);
    PayOS existingOrder = orderRepository.findById((long) orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", orderId));
    existingOrder.setWebhook_snapshot(paymentData.get("webhookSnapshot").asText());
    existingOrder.setStatus("PAID");
    existingOrder.setRef_id(paymentData.get("refId").asText());
    existingOrder.setTransaction_when(LocalDateTime.parse(paymentData.get("when").asText(),
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    existingOrder.setTransaction_code(paymentData.get("code").asText());
    orderRepository.save(existingOrder);

  }
}
