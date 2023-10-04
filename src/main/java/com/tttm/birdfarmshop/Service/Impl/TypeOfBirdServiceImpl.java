package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.DTO.TypeOfBirdDTO;
import com.tttm.birdfarmshop.Models.TypeOfBird;
import com.tttm.birdfarmshop.Repository.TypeOfBirdRepository;
import com.tttm.birdfarmshop.Service.TypeOfBirdService;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeOfBirdServiceImpl implements TypeOfBirdService {

    private final TypeOfBirdRepository typeOfBirdRepository;
    private final Logger logger = LoggerFactory.getLogger(TypeOfBirdServiceImpl.class);
    private boolean isValidTypeOfBird(TypeOfBirdDTO dto) {
        return !dto.getTypeName().isBlank()
                && !dto.getTypeName().isEmpty()
                && dto.getQuantity() >= 0;
    }

    @Override
    public MessageResponse AddNewTypeOfBird(TypeOfBirdDTO dto) {
        int size = (int) typeOfBirdRepository.findAll().stream().count();
        String typeID = "TB00" + (size + 1);

        if(isValidTypeOfBird(dto)) {
            typeOfBirdRepository.save(new TypeOfBird(typeID, dto.getTypeName(), dto.getQuantity()));
            return new MessageResponse("Add new Type Of Bird Successfully");
        }
        return new MessageResponse("Fail to Add Type Of Bird");
    }

    @Override
    public MessageResponse UpdateTypeOfBird(String typeID, TypeOfBirdDTO dto) {
        if(typeOfBirdRepository.findById(typeID).get() == null)
        {
            return new MessageResponse("TypeID is not existed");
        }
        if(isValidTypeOfBird(dto)) {
            return new MessageResponse(
                    Optional
                        .ofNullable(typeOfBirdRepository.findById(typeID).get())
                        .map(typeOfBird -> {
                                typeOfBird.setTypeName(dto.getTypeName());
                                typeOfBird.setQuantity(dto.getQuantity());
                                typeOfBirdRepository.save(typeOfBird);
                                return "Update Type Of Bird Successfully";
                        })
                        .orElse("Fail to Update Type Of Bird")
            );
        }
        else return new MessageResponse("Invalid type of input. Fail to update Type Of Bird");
    }

    @Override
    public TypeOfBird findTypeOfBirdByID(String typeID) {
        return typeOfBirdRepository.findById(typeID)
                .orElseThrow(() -> new NotFoundException("Type Of Bird not found with ID: " + typeID));
    }

    @Override
    public List<TypeOfBird> findAllTypeOfBird() {
        return typeOfBirdRepository.findAll();
    }
}
