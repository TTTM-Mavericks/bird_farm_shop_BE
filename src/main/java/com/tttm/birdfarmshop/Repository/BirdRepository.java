package com.tttm.birdfarmshop.Repository;

import com.tttm.birdfarmshop.Models.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirdRepository extends JpaRepository<Bird, String> {
}
