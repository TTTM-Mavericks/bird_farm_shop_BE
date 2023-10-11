package com.tttm.birdfarmshop.Repository;

import com.tttm.birdfarmshop.Models.User;
import com.tttm.birdfarmshop.Models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    @Query(
            value = "select * from voucher where voucher_name = ?1", nativeQuery = true
    )
    Voucher findVoucherByVoucherName(String voucherName);
}
