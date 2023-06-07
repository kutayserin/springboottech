package com.kutay.springboottech.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "History")
@Data
public class History {

    public History() {}

    public History(String userEmail, String checkoutDate, String boughtDate, String title,
                   String brand, double price, String description, String img){
        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.boughtDate = boughtDate;
        this.title = title;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.img = img;
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

        @Column(name = "title")
        private String title;

        @Column(name = "brand")
        private String brand;

    @Column(name = "price")
    private double price;

        @Column(name = "description")
        private String description;

        @Column(name = "img")
        private String img;

}
