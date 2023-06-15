package com.kutay.springboottech.service;


import com.kutay.springboottech.dao.ProductRepository;
import com.kutay.springboottech.dao.ReviewRepository;
import com.kutay.springboottech.entity.Review;
import com.kutay.springboottech.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;


@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception{
        Review validateReview = reviewRepository.findByUserEmailAndProductId(userEmail,reviewRequest.getProductId());

        if(validateReview != null){
            throw new Exception("Review already created.");
        }

        Review review = new Review();
        review.setProductId(reviewRequest.getProductId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if(reviewRequest.getReviewDescription().isPresent()){
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString //Changing the description to a string(method reference)
            ).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);


    }

    public Boolean userReviewListed(String userEmail, Long productId){
        Review validateReview = reviewRepository.findByUserEmailAndProductId(userEmail,productId);
        if(validateReview != null) {
           return true;
        }
        else {
            return false;
        }

    }

}
