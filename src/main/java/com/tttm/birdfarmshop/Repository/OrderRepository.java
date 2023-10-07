package com.tttm.birdfarmshop.Repository;

import com.tttm.birdfarmshop.Models.Order;
import com.tttm.birdfarmshop.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
