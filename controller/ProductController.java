package com.kutay.springboottech.controller;

import com.kutay.springboottech.entity.Product;
import com.kutay.springboottech.responsemodels.CartCurrentLoansResponse;
import com.kutay.springboottech.service.ProductService;
import com.kutay.springboottech.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/secure/currentloans")
    public List<CartCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        return productService.currentLoans(userEmail);

    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return productService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser")
        public Boolean checkoutProductByUser(@RequestHeader(value = "Authorization") String token
            ,@RequestParam Long productId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        return productService.checkoutProductByUser(userEmail,productId);
        }


    @PutMapping("/secure/checkout")
    public Product checkoutProduct (@RequestHeader(value = "Authorization") String token,
                              @RequestParam Long productId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return productService.checkoutProduct(userEmail,productId);

    }

     @DeleteMapping("/secure/delete/product")
    public void deleteProduct(@RequestHeader(value = "Authorization") String token,
                           @RequestParam Long productId) throws Exception{
         String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
         if(userEmail == null){
             throw new Exception("This page is for users.");
         }
         productService.deleteProduct(productId);

     }

     @PutMapping("secure/buy")
     public void buyProduct(@RequestHeader(value = "Authorization") String token,
                            @RequestParam Long productId) throws Exception{
         String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

         productService.buyProduct(userEmail, productId);
     }

    // @PutMapping("/secure/renew/loan")
    // public void renewLoan(@RequestHeader(value = "Authorization") String token,
     //                       @RequestParam Long productId) throws Exception{
     //    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

      //   productService.renewLoan(userEmail, productId);

    // }

}
