package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.Models.HealthcareProfessional;
import com.tttm.birdfarmshop.Models.Shipper;
import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Repository.ShipperRepository;
import com.tttm.birdfarmshop.Service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService {
    private final ShipperRepository shipperRepository;
    private static final Logger logger = LogManager.getLogger(ShipperServiceImpl.class);
    @Override
    public void createShipper(User user) {
        shipperRepository.save(new Shipper(user.getUserID()));
        logger.info("Create new Shipper Successfully");
    }
}
