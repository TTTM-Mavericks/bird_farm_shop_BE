package com.tttm.birdfarmshop.Repository;

import com.tttm.birdfarmshop.Models.TypeOfBird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfBirdRepository extends JpaRepository<TypeOfBird, String> {
}
