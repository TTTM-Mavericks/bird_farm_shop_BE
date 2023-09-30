package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.Models.HealthcareProfessional;
import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Repository.HealthcareProfessionalRepository;
import com.tttm.birdfarmshop.Service.HealthcareProfessionalService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthcareProfessionalServiceImpl implements HealthcareProfessionalService {
    private final HealthcareProfessionalRepository healthcareProfessionalRepository;
    private static final Logger logger = LogManager.getLogger(HealthcareProfessionalServiceImpl.class);

    @Override
    public void createHealthcareProfessional(User user) {
        healthcareProfessionalRepository.save(new HealthcareProfessional(user.getUserID()));
        logger.info("Create new Healthcare Professional Successfully");
    }
}
