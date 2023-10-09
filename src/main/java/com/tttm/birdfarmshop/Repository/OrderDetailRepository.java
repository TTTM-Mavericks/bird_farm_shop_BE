package com.tttm.birdfarmshop.Repository;

import com.tttm.birdfarmshop.Models.Order;
import com.tttm.birdfarmshop.Models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query(
            value = "select * from order_detail where orderid = ?1", nativeQuery = true
    )
    List<OrderDetail> findAllOrderDetailByOrderID(Integer orderID);
}
