package com.kutay.springboottech.entity;


import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "checkout")
@Data
public class Checkout {

    public Checkout() {}

    public Checkout(String userEmail, String checkoutDate, String boughtDate, Long productId){

        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.boughtDate = boughtDate;
        this.productId = productId;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "checkout_date")
    private String checkoutDate;

    @Column(name = "bought_date")
    private String boughtDate;

    @Column(name = "product_id")
    private Long productId;



}
