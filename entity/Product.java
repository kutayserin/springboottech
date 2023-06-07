package com.kutay.springboottech.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "brand")
    private String brand;
    
    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "stock")
    private int stock;

    @Column(name = "stockAvailable")
    private int stockAvailable;

    @Column(name = "category")
    private String category;

    @Column(name = "img")
    private String img;

    //@Version
    //private long versıon;
}
