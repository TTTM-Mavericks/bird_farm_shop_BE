package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.DTO.TypeOfBirdDTO;
import com.tttm.birdfarmshop.Models.TypeOfBird;
import com.tttm.birdfarmshop.Repository.TypeOfBirdRepository;
import com.tttm.birdfarmshop.Service.TypeOfBirdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeOfBirdServiceImpl implements TypeOfBirdService {
    private final TypeOfBirdRepository typeOfBirdRepository;
    @Override
    public String AddNewTypeOfBird(TypeOfBirdDTO dto) {
        Optional.ofNullable(dto).ifPresent(type -> {
            typeOfBirdRepository.save(new TypeOfBird(dto.getTypeName(), dto.getQuantity()));
        });
    }

    @Override
    public String UpdateTypeOfBird(Integer typeID, TypeOfBirdDTO dto) {
        TypeOfBird typeOfBird = typeOfBirdRepository.findById(typeID).get();
        if(typeOfBird != null)
        {
            typeOfBird.setTypeName(dto.getTypeName());
            typeOfBird.setQuantity(dto.getQuantity());
            typeOfBirdRepository.save(typeOfBird);
        }
    }

    @Override
    public TypeOfBird findTypeOfBirdByID(Integer typeID) {
        return null;
    }

    @Override
    public Enumeration<TypeOfBird> findAllTypeOfBird() {
        return null;
    }
}
