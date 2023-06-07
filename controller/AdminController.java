package com.kutay.springboottech.controller;

import com.kutay.springboottech.requestmodels.AddProductRequest;
import com.kutay.springboottech.requestmodels.AdminQuestionRequest;
import com.kutay.springboottech.service.AdminService;
import com.kutay.springboottech.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PutMapping("/secure/increase/product/quantity")
    public void increaseProductQuantity(@RequestHeader(value = "Authorization") String token,
                                     @RequestParam Long productId) throws Exception{
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if(admin == null || !admin.equals("admin")){
            throw new Exception("Administration page only.");
        }
        adminService.increaseProductQuantity(productId);
    }

    @PutMapping("/secure/decrease/product/quantity")
    public void decreaseProductQuantity(@RequestHeader(value = "Authorization") String token,
                                     @RequestParam Long productId) throws Exception{
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if(admin == null || !admin.equals("admin")){
            throw new Exception("Administration page only.");
        }
        adminService.decreaseProductQuantity(productId);
    }

    @PostMapping("/secure/add/product")
    public void postProduct(@RequestHeader(name = "Authorization") String token,
                         @RequestBody AddProductRequest addProductRequest) throws Exception{
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if(admin == null || !admin.equals("admin")){
            throw new Exception("Administration page only.");
        }
        adminService.postProduct(addProductRequest);
    }

    @DeleteMapping("/secure/delete/product")
    public void postProduct(@RequestHeader(name = "Authorization") String token,
                         @RequestParam Long productId) throws Exception{

        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if(admin == null || !admin.equals("admin")){
            throw new Exception("Administration page only.");
        }
        adminService.deleteProduct(productId);


    }


}
