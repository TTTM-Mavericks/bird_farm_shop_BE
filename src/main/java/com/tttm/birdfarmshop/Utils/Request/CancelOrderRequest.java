package com.tttm.birdfarmshop.Utils.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelOrderRequest {
    private Integer customerId;

    private String email;

    private Integer orderID;
}
