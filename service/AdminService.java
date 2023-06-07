package com.kutay.springboottech.service;

import com.kutay.springboottech.dao.ProductRepository;
import com.kutay.springboottech.dao.CheckoutRepository;
import com.kutay.springboottech.dao.ReviewRepository;
import com.kutay.springboottech.entity.Product;
import com.kutay.springboottech.entity.Checkout;
import com.kutay.springboottech.entity.Review;
import com.kutay.springboottech.requestmodels.AddProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    private CheckoutRepository checkoutRepository;

    @Autowired
    public AdminService(ProductRepository productRepository, ReviewRepository reviewRepository, CheckoutRepository checkoutRepository){
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.checkoutRepository = checkoutRepository;

    }

    public void increaseProductQuantity(Long productId) throws Exception{
        Optional<Product> product = productRepository.findById(productId);

        if(!product.isPresent()){
            throw new Exception("Product not found");
        }
        product.get().setStockAvailable(product.get().getStockAvailable() + 1);
        product.get().setStock(product.get().getStock() + 1);

        productRepository.save(product.get());
    }

    public void decreaseProductQuantity(Long productId) throws Exception{
        Optional<Product> product = productRepository.findById(productId);

        if(!product.isPresent() || product.get().getStockAvailable() <= 0 || product.get().getStock() <= 0){
            throw new Exception("Product not found or quantity locked");
        }
        product.get().setStockAvailable(product.get().getStockAvailable() - 1);
        product.get().setStock(product.get().getStock() - 1);

        productRepository.save(product.get());
    }

    public void deleteProduct(Long productId) throws Exception{
        Optional<Product> product = productRepository.findById(productId);

        if(!product.isPresent()){
            throw new Exception("Product not found");
        }

        productRepository.delete(product.get());
        checkoutRepository.deleteAllByProductId(productId);
        reviewRepository.deleteAllByProductId(productId);
    }



    public void postProduct(AddProductRequest addProductRequest){
        Product product = new Product();

        product.setTitle(addProductRequest.getTitle());
        product.setBrand(addProductRequest.getBrand());
        product.setCategory(addProductRequest.getCategory());
        product.setDescription(addProductRequest.getDescription());
        product.setPrice(addProductRequest.getPrice());
        product.setImg(addProductRequest.getImg());
        product.setStock(addProductRequest.getStock());
        product.setStockAvailable(addProductRequest.getStock());

        productRepository.save(product);
    }
}
