package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.Utils.Request.VoucherRequest;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import com.tttm.birdfarmshop.Utils.Response.VoucherResponse;

import java.util.List;

public interface VoucherService {
    MessageResponse createVoucher(VoucherRequest voucherRequest);
    VoucherResponse getVoucherByID(Integer voucherID);
    MessageResponse updateVoucher(Integer voucherID, VoucherRequest voucherRequest);
    List<VoucherResponse> getAllVoucher();
}
