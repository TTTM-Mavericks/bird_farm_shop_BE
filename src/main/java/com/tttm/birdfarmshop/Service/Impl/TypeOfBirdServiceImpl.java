package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.DTO.TypeOfBirdDTO;
import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Models.TypeOfBird;
import com.tttm.birdfarmshop.Repository.TypeOfBirdRepository;
import com.tttm.birdfarmshop.Service.TypeOfBirdService;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import com.tttm.birdfarmshop.Utils.Response.TypeOfBirdResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
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
            typeOfBirdRepository.save(new TypeOfBird(typeID, dto.getTypeName(), 0));
            return new MessageResponse("Success");
        }
        return new MessageResponse("Fail");
    }

    @Override
    public MessageResponse UpdateTypeOfBird(String typeID, TypeOfBirdDTO dto) {
       try
       {
           Optional<TypeOfBird> TypeOfBirdOptional = typeOfBirdRepository.findById(typeID);
           if(TypeOfBirdOptional.isEmpty() || !isValidTypeOfBird(dto))
           {
               return new MessageResponse("Fail");
           }

           return new MessageResponse(
                   Optional
                           .ofNullable(typeOfBirdRepository.findById(typeID).get())
                           .map(typeOfBird -> {
                               typeOfBird.setTypeName(dto.getTypeName());
                               typeOfBird.setQuantity(dto.getQuantity());
                               typeOfBirdRepository.save(typeOfBird);
                               return "Success";
                           })
                           .orElse("Fail"));
       }
       catch (Exception exception)
       {
           return new MessageResponse("Fail");
       }
    }

    @Override
    public TypeOfBirdResponse findTypeOfBirdByID(String typeID) {
        try{
            Optional<TypeOfBird> TypeOfBirdOptional = typeOfBirdRepository.findById(typeID);
            if(TypeOfBirdOptional.isPresent())
            {
                return mapperedToTypeOfBirdResponse(TypeOfBirdOptional.get());
            }
            else return new TypeOfBirdResponse();
        }
        catch (Exception e)
        {
            return new TypeOfBirdResponse();
        }
    }

    @Override
    public List<TypeOfBirdResponse> findAllTypeOfBird() {
        List<TypeOfBirdResponse> typeOfBirdResponseList = new ArrayList<>();
        List<TypeOfBird> list = typeOfBirdRepository.findAll();
        for(TypeOfBird typeOfBird : list)
        {
            typeOfBirdResponseList.add(mapperedToTypeOfBirdResponse(typeOfBird));
        }
        return typeOfBirdResponseList;
    }

    private TypeOfBirdResponse mapperedToTypeOfBirdResponse(TypeOfBird typeOfBird)
    {
        return new TypeOfBirdResponse(
                typeOfBird.getTypeID(),
                typeOfBird.getTypeName(),
                typeOfBird.getQuantity()
        );
    }
}
