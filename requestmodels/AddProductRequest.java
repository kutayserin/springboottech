package com.kutay.springboottech.requestmodels;

import lombok.Data;

@Data
public class AddProductRequest {
    private String title;

    private String brand;

    private double price;

    private String description;

    private int stock;

    private String category;

    private String img;
}
