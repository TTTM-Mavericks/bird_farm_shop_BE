package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.Enums.VoucherStatus;
import com.tttm.birdfarmshop.Models.Seller;
import com.tttm.birdfarmshop.Models.Voucher;
import com.tttm.birdfarmshop.Repository.SellerRepository;
import com.tttm.birdfarmshop.Repository.VoucherRepository;
import com.tttm.birdfarmshop.Service.VoucherService;
import com.tttm.birdfarmshop.Utils.Request.VoucherRequest;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import com.tttm.birdfarmshop.Utils.Response.VoucherResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final SellerRepository sellerRepository;

    private final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);
    private boolean isValidInformation(VoucherRequest voucherRequest)
    {
        return !voucherRequest.getVoucherName().isEmpty() && !voucherRequest.getVoucherName().isBlank() &&
                !voucherRequest.getStartDate().toString().isEmpty() && !voucherRequest.getVoucherName().toString().isBlank() &&
                !voucherRequest.getEndDate().toString().isEmpty() && !voucherRequest.getEndDate().toString().isBlank() &&
                voucherRequest.getValue() >= 0;
    }
    @Override
    public MessageResponse createVoucher(VoucherRequest voucherRequest) {
        Voucher voucher = voucherRepository.findVoucherByVoucherName(voucherRequest.getVoucherName());
        if(voucher != null || !isValidInformation(voucherRequest))
        {
            logger.warn("Voucher Name is existed or Invalid Information");
            return new MessageResponse("Fail");
        }

        // Give random seller to take care of voucher
        List<Seller> sellerList = sellerRepository.findAll();
        Random random = new Random();
        int sellerIndex = random.nextInt(sellerList.size());
        Seller seller = sellerList.get(sellerIndex);

        voucherRepository.save(new Voucher(
                voucherRequest.getVoucherName(),
                voucherRequest.getStartDate(),
                voucherRequest.getEndDate(),
                voucherRequest.getValue(),
                seller,
                VoucherStatus.AVAILABLE
        ));
        return new MessageResponse("Success");
    }

    @Override
    public VoucherResponse getVoucherByID(Integer voucherID) {
        return voucherRepository.findById(voucherID)
                .map(voucher -> {
                    if(!isValidVoucher(voucher))
                    {
                        voucher.setVoucherStatus(VoucherStatus.UNAVAILABLE);
                    }
                    else  voucher.setVoucherStatus(VoucherStatus.AVAILABLE);
                    return createVoucherResponse(voucher);
                })
                .orElse(new VoucherResponse());
    }

    @Override
    public MessageResponse updateVoucher(Integer voucherID, VoucherRequest voucherRequest) {
        if(!isValidInformation(voucherRequest))
        {
            return new MessageResponse("Fail");
        }
        return voucherRepository.findById(voucherID)
                .map(voucher -> {
                    voucher.setVoucherName(voucherRequest.getVoucherName());
                    voucher.setStartDate(voucherRequest.getStartDate());
                    voucher.setEndDate(voucherRequest.getEndDate());
                    voucher.setValue(voucherRequest.getValue());
                    if(!isValidVoucher(voucher))
                    {
                        voucher.setVoucherStatus(VoucherStatus.UNAVAILABLE);
                    }
                    else voucher.setVoucherStatus(VoucherStatus.AVAILABLE);
                    voucherRepository.save(voucher);
                    return new MessageResponse("Success");
                })
                .orElse(new MessageResponse("Fail"));
    }

    private boolean isValidVoucher(Voucher voucher)
    {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            Date systemDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateString = simpleDateFormat.format(systemDate);
//            logger.info("Current Date: " + currentDateString);
//            logger.info("Start Date: " + voucher.getStartDate());
//            logger.info("End Date: " + voucher.getEndDate());
            if(voucher.getStartDate().toString().equals(currentDateString) || voucher.getEndDate().toString().equals(currentDateString))
            {
                return true;
            }
            if(voucher.getStartDate().compareTo(systemDate) <= 0 && systemDate.compareTo(voucher.getEndDate()) <= 0)
            {
                return true;
            }
            return false;
        }
        catch (Exception exception)
        {
            return false;
        }
    }
    @Override
    public List<VoucherResponse> getAllVoucher() {
        return voucherRepository.findAll()
                .stream()
                .map(voucher -> {
                    if(!isValidVoucher(voucher))
                    {
                        voucher.setVoucherStatus(VoucherStatus.UNAVAILABLE);
                    }
                    else  voucher.setVoucherStatus(VoucherStatus.AVAILABLE);
                    voucherRepository.save(voucher);
                    return createVoucherResponse(voucher);
                })
                .collect(Collectors.toList());
    }

    private VoucherResponse createVoucherResponse(Voucher voucher)
    {
        return new VoucherResponse(
                voucher.getVoucherID(),
                voucher.getVoucherName(),
                voucher.getStartDate(),
                voucher.getEndDate(),
                voucher.getValue(),
                voucher.getSeller().getSellerID(),
                voucher.getVoucherStatus().toString()
        );
    }
}
