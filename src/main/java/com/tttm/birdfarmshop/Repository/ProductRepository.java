package com.tttm.birdfarmshop.Repository;

import com.tttm.birdfarmshop.Models.Product;
import com.tttm.birdfarmshop.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(
            value = "select * from product where productid like '%F%'", nativeQuery = true
    )
    List<Product> findAllFood();

    @Query(
            value = "select * from product where productid like '%N%'", nativeQuery = true
    )
    List<Product> findAllNest();

    @Query(
            value = "select * from product where productid like '%B%'", nativeQuery = true
    )
    List<Product> findAllBird();
}
