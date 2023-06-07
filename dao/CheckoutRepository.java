package com.kutay.springboottech.dao;

import com.kutay.springboottech.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndProductId(String userEmail, Long productId);

    List<Checkout> findProductsByUserEmail(String userEmail);


   // @Query("select sum(price) from Checkout where user_email = userEmail")
    //double getTotalPriceByUserEmail(@Param("userEmail") String userEmail);


    @Modifying
    @Query("delete from Checkout where product_id in :product_id")
    void deleteAllByProductId(@Param("product_id") Long productId);

}
