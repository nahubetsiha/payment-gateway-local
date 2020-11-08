package com.pm.paymentgateway.service.impl;

import com.pm.paymentgateway.exception.EntityNotFoundException;
import com.pm.paymentgateway.exception.InvalidPaymentException;
import com.pm.paymentgateway.model.*;
//import com.pm.paymentgateway.repository.OrderRepository;
import com.pm.paymentgateway.repository.OrderRepository;
import com.pm.paymentgateway.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentGatewayService {

    @Autowired
    private KafkaTemplate<Object, Object> producer;

    CardService masterCardService;
    CardService visaService;
    TransactionService<MasterCardTransaction> mTransactionService;
    TransactionService<VisaTransaction> vTransactionService;
    OrderRepository orderRepository;

    public PaymentGatewayService(@Qualifier("masterCardService") CardService<MasterCard> masterCardService, @Qualifier("visaService") CardService<Visa> visaService,
                                  @Qualifier("MTransactionServiceImpl") TransactionService<MasterCardTransaction> mTransactionService,
                                 @Qualifier("VTransactionServiceImpl") TransactionService<VisaTransaction> vTransactionService, OrderRepository orderRepository){
        this.masterCardService = masterCardService;
        this.visaService = visaService;
        this.mTransactionService = mTransactionService;
        this.vTransactionService = vTransactionService;
        this.orderRepository = orderRepository;
    }

    @KafkaListener(groupId = "order", topics = "Order-Created")
    public Order processTransaction(Order order){

        CardInformation card = order.getCardInfo();
        List<PayTo> payTo = order.getPayTo();


        if(card==null){
            throw new EntityNotFoundException(CardInformation.class);
        }


        String ccNumber = card.getCardNumber();
        int length = String.valueOf(ccNumber).length();
        char firstDigit = String.valueOf(ccNumber).charAt(0);

        System.out.println("credit card length: "+length + " first digit: "+firstDigit);
        System.out.println(String.valueOf(ccNumber).charAt(0));

        if(length==16 && firstDigit=='5'){
            MasterCard masterCard = (MasterCard) masterCardService.getByCardNumber(ccNumber);
            System.out.println(masterCard.getCardNumber());
            masterCardService.processTransaction(masterCard, payTo);

        }
        else if (length==16 && firstDigit=='4'){
            Visa visa = (Visa) visaService.getByCardNumber(ccNumber);

            visaService.processTransaction(visa, payTo);
        }
        else throw new InvalidPaymentException("Payment Transaction failed");


        ProductDto productDto = new ProductDto();
        productDto.setOrderId(order.getOrderId());

        for(PayTo p: order.getPayTo()){
            Product product = new Product();
            product.setProductId(p.getProductId());
            product.setQuantity(p.getQuantity());
            productDto.setUserEmail(order.getUserEmail());
            productDto.getProducts().add(product);
        }
        System.out.println(order.getUserEmail());
        producer.send("Payment-Being-Paid",productDto);
        return orderRepository.save(order);
    }



    public <T> T verifyCard(CardInformation card){
        if(card==null){
            throw new EntityNotFoundException(CardInformation.class);
        }


        String ccNumber = card.getCardNumber();
        int length = ccNumber.length();
        char firstDigit = ccNumber.charAt(0);

//        System.out.println("credit card length: "+length + " first digit: "+firstDigit);
//        System.out.println(String.valueOf(ccNumber).charAt(0));

        if(length==16 && firstDigit=='5'){
            MasterCard masterCard = (MasterCard) masterCardService.getByCardNumber(ccNumber);
            if(masterCard==null) throw new InvalidPaymentException("Card Not Found");

            masterCard.setName(card.getName());
            masterCard.setPin(card.getPin());
            masterCard.setCardNumber(card.getCardNumber());
            masterCard.setExpDate(card.getExpDate());
            return (T)masterCard;

        }
        else if (length==16 && firstDigit=='4'){
            Visa visa = (Visa) visaService.getByCardNumber(ccNumber);
            if(visa==null) throw new InvalidPaymentException("Card Not Found");

            visa.setName(card.getName());
            visa.setPin(card.getPin());
            visa.setCardNumber(card.getCardNumber());
            visa.setExpDate(card.getExpDate());
            return  (T) visa;
        }
        else throw new InvalidPaymentException("Card Not Accepted");

    }

    //    @KafkaListener(groupId = "payReverse", topics = "Fail-Qty-Deduction")
//    public void reverseTransaction(Long orderId){
//        Order order = orderRepository.getOne(orderId);
//
//        if(order==null){
//            throw new EntityNotFoundException(Order.class);
//        }
//
//        String ccNumber = order.getCardInfo().getCardNumber();
//        char firstDigit = String.valueOf(ccNumber).charAt(0);
//
//        if(firstDigit=='5') {
//            MasterCard masterCard = (MasterCard) masterCardService.getByCardNumber(ccNumber);
//            for(PayTo p: order.getPayTo()){
//                masterCard.setAvailableBalance(masterCard.getAvailableBalance() + p.getPrice());
//                masterCardService.updateCard(masterCard, masterCard.getMasterCardId());
//            }
//
//        }
//
//        else if(firstDigit=='4'){
//            Visa visa = (Visa) visaService.getByCardNumber(ccNumber);
//            for(PayTo p: order.getPayTo()){
//                visa.setAvailableBalance(visa.getAvailableBalance() + p.getPrice());
//                masterCardService.updateCard(visa, visa.getVisaCardId());
//            }
//        }
//
//    }
}
