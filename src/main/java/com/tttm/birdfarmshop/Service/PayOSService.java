package com.tttm.birdfarmshop.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tttm.birdfarmshop.Models.PayOS;

public interface PayOSService {
  void CreateOrder(PayOS order);
  PayOS getOrder(int orderId);
  void updatePaymentForOrder(int orderId, JsonNode paymentData);
}
