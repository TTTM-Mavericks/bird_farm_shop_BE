package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.DTO.TypeOfBirdDTO;
import com.tttm.birdfarmshop.Models.TypeOfBird;

import java.util.Enumeration;

public interface TypeOfBirdService {
    String AddNewTypeOfBird(TypeOfBirdDTO dto);

    String UpdateTypeOfBird(Integer typeID, TypeOfBirdDTO dto);

    TypeOfBird findTypeOfBirdByID(Integer typeID);

    Enumeration<TypeOfBird> findAllTypeOfBird();

}
