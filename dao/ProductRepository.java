package com.kutay.springboottech.dao;

import com.kutay.springboottech.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

    Page<Product> findByCategory(@RequestParam("category") String category, Pageable pageable);

    @Query("select o from Product o where id in :product_ids")
    List<Product> findProductsByProductIds(@Param("product_ids") List<Long> productId);

    //@Query("select o from Product o where id = :product_id")
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    //Optional<Product> fındbyIdMyVersıon(@Param("product_ıd") Long productId);
}
