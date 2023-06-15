package com.kutay.springboottech.service;


import com.kutay.springboottech.dao.ProductRepository;
import com.kutay.springboottech.dao.CheckoutRepository;
import com.kutay.springboottech.dao.HistoryRepository;
import com.kutay.springboottech.dao.PaymentRepository;
import com.kutay.springboottech.entity.Product;
import com.kutay.springboottech.entity.Checkout;
import com.kutay.springboottech.entity.History;
import com.kutay.springboottech.entity.Payment;
import com.kutay.springboottech.responsemodels.CartCurrentLoansResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.servlet.function.ServerResponse.async;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;

    private CheckoutRepository checkoutRepository;

    private HistoryRepository historyRepository;

    private PaymentRepository paymentRepository;

    public ProductService(ProductRepository productRepository, CheckoutRepository checkoutRepository,
                       HistoryRepository historyRepository, PaymentRepository paymentRepository){
        this.productRepository = productRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
        this.paymentRepository = paymentRepository;
    }

    //readOnly = false, rollbackFor = Exception.class
    public Product checkoutProduct (String userEmail, Long productId) throws Exception {
        Optional<Product> product = productRepository.findById(productId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndProductId(userEmail, productId);
                           // || validateCheckout != null
        if (!product.isPresent()  || product.get().getStockAvailable() <= 0) {
            throw new Exception("Product doesn't exist or already checked out by user");
        }

        List<Checkout> currentProductsCheckedOut = checkoutRepository.findProductsByUserEmail(userEmail);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //boolean productNeedsReturned = false;

      // for (Checkout checkout : currentProductsCheckedOut) {
      //     Date d1 = sdf.parse(checkout.getReturnDate());
      //     Date d2 = sdf.parse(LocalDate.now().toString());
      //
      //     TimeUnit time = TimeUnit.DAYS;
      //
      //     double differenceInTıme = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);
      //
      //     if (differenceInTıme < 0) {
      //         productNeedsReturned = true;
      //         break;
      //     }
        

        Payment userPayment = paymentRepository.findByUserEmail(userEmail);

      if ((userPayment != null && userPayment.getAmount() > 0)){
         throw new Exception("Outstanding fees");
      }

        if (userPayment == null){
            Payment payment = new Payment();
            payment.setAmount(00.00);
            payment.setUserEmail(userEmail);
            paymentRepository.save(payment);
        }

        if(product.get().getStockAvailable()<1) throw new Exception();
        product.get().setStockAvailable(product.get().getStockAvailable() - 1);
        productRepository.save(product.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(15).toString(),
                product.get().getId()
        );

        checkoutRepository.save(checkout);

        return product.get();

    }
    public Boolean checkoutProductByUser (String userEmail, Long productId){
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndProductId(userEmail, productId);
        if(validateCheckout != null) {
            return true;
        }
        else {
            return false;
        }
    }


    public int currentLoansCount(String userEmail){
        return checkoutRepository.findProductsByUserEmail(userEmail).size();
    }

    public List<CartCurrentLoansResponse> currentLoans(String userEmail) throws Exception{

        List<CartCurrentLoansResponse> cartCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findProductsByUserEmail(userEmail);

        List<Long> productIdList = new ArrayList<>();

        for(Checkout i: checkoutList){
            productIdList.add(i.getProductId() );
        }

        List<Product> products = productRepository.findProductsByProductIds(productIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for(Product product : products) {
            Optional<Checkout> checkout = checkoutList
                    .stream()
                    .filter(x -> x.getProductId() == product.getId()).findFirst();

           // if (checkout.isPresent()){
           //     Date d1 = sdf.parse(checkout.get().getBoughtDate());
           //     Date d2 = sdf.parse(LocalDate.now().toString());

           //     TimeUnit time = TimeUnit.DAYS;

           //     long difference_In_Time = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

                cartCurrentLoansResponses.add(new CartCurrentLoansResponse(product, product.getStockAvailable()));


        }
        return cartCurrentLoansResponses;

    }

    public void deleteProduct(Long productId) throws Exception{


        Optional<Product> product = productRepository.findById(productId);

        if(!product.isPresent()){
            throw new Exception("Product not found");
        }

        checkoutRepository.deleteAllByProductId(productId);

        product.get().setStockAvailable(product.get().getStockAvailable() + 1);

        productRepository.save(product.get());


    }

    public void buyProduct(String userEmail, Long productId) throws Exception{
        Optional<Product> product = productRepository.findById(productId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndProductId(userEmail, productId);

        if(!product.isPresent() || validateCheckout == null) {
            throw new Exception("Product does not exist or not checked out by user");
        }

       // product.get().setStockAvailable(product.get().getStockAvailable() + 1);

        //productRepository.save(product.get());

       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

       // Date d1 = sdf.parse(validateCheckout.getReturnDate());
       // Date d2 = sdf.parse(LocalDate.now().toString());

        //TimeUnit time = TimeUnit.DAYS;

        //double differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

        //if(differenceInTime < 15 && differenceInTime > 0)
        //double totalPrice = checkoutRepository.getTotalPriceByUserEmail(userEmail);


            Payment payment = paymentRepository.findByUserEmail(userEmail);

            payment.setAmount(product.get().getPrice());
            paymentRepository.save(payment);

        {

                checkoutRepository.deleteById(validateCheckout.getId());


                History history = new History(
                        userEmail,
                        validateCheckout.getCheckoutDate(),
                        LocalDate.now().toString(),
                        product.get().getTitle(),
                        product.get().getBrand(),
                        product.get().getPrice(),
                        product.get().getDescription(),
                        product.get().getImg());


                historyRepository.save(history);
            }







        }



 // Bunu silcez.
  //  public void renewLoan(String userEmail, Long productId) throws Exception{
    //    Checkout validateCheckout = checkoutRepository.findByUserEmailAndProductId(userEmail, productId);

      //  if(validateCheckout == null) {
        //    throw new Exception("Product does not exist or not checked out by user");
       // }

      //  SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

      //  Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
      //  Date d2 = sdFormat.parse(LocalDate.now().toString());

      //  if(d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0){
       //     validateCheckout.setReturnDate(LocalDate.now().plusDays(15).toString());
       //     checkoutRepository.save(validateCheckout);
       // }

    }



