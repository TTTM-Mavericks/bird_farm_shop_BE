package com.tttm.birdfarmshop.Repository;

import com.tttm.birdfarmshop.Models.Order;
import com.tttm.birdfarmshop.Models.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Modifying
    @Query(value = "delete from `order` where orderid = ?1", nativeQuery = true)
    void deleteOrderByOrderID(Integer orderID);
}
