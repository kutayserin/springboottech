package com.kutay.springboottech.responsemodels;

import com.kutay.springboottech.entity.Product;
import lombok.Data;

@Data
public class CartCurrentLoansResponse {

    public CartCurrentLoansResponse(Product product, int productLeft){
        this.product = product;
        this.productLeft = productLeft;
    }

    private Product product;

    private int productLeft;

}
