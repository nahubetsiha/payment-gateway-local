//package com.pm.paymentgateway.service.impl;
//
//import com.pm.paymentgateway.exception.EntityNotFoundException;
//import com.pm.paymentgateway.model.Recipient;
//import com.pm.paymentgateway.repository.RecipientRepository;
//import com.pm.paymentgateway.service.RecipientService;
//import org.springframework.stereotype.Service;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@Service
//public class RecipientServiceImpl implements RecipientService {
//
//    RecipientRepository recipientRepository;
//
//    public RecipientServiceImpl(RecipientRepository recipientRepository){
//        this.recipientRepository = recipientRepository;
//    }
//
//    @Override
//    public List<Recipient> getAllRecipients() {
//
//        List<Recipient> recipients = recipientRepository.findAll();
//
//        if(recipients == null) throw new EntityNotFoundException(Recipient.class);
//
//        return recipients;
//    }
//
//    @Override
//    public Recipient getRecipient(Long recipientId) {
//        Recipient recipient = recipientRepository.getOne(recipientId);
//        if(recipient == null) throw new EntityNotFoundException(Recipient.class, recipientId);
//
//        return recipient;
//    }
//
//    @Override
//    public Recipient getRecipientByAccountNo(Long accountNo) {
//        return recipientRepository.getByAccountNo(accountNo);
//    }
//
//    @Override
//    public Recipient addRecipient(@Valid Recipient recipient) {
//        return recipientRepository.save(recipient);
//    }
//
//    @Override
//    public Recipient updateRecipient(@Valid Recipient recipient, Long recipientId) {
//
//        return recipientRepository.findById(recipientId)
//                .map(recipientToUpdate -> {
//                    recipientToUpdate.setAccountNo(recipient.getAccountNo());
//                    recipientToUpdate.setBalance(recipient.getBalance());
//                    recipientToUpdate.setRecipientName(recipient.getRecipientName());
//
//                    return recipientToUpdate;
//                }).orElseThrow(() -> new EntityNotFoundException(Recipient.class, recipientId));
//    }
//
//    @Override
//    public Long deleteRecipient(Long recipientId) {
//        Recipient recipient = recipientRepository.getOne(recipientId);
//        if(recipient == null) throw new EntityNotFoundException(Recipient.class, recipientId);
//
//        recipientRepository.delete(recipient);
//        return recipientId;
//    }
//}
