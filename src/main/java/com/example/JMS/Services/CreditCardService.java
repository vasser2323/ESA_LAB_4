package com.example.JMS.Services;

import com.example.JMS.models.CreditCard;
import com.example.JMS.repositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public Iterable<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public Optional<CreditCard> getCreditCardById(Integer id) {
        return creditCardRepository.findById(id);
    }

    public CreditCard createOrUpdateCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    public void deleteCreditCardById(Integer id) {
        creditCardRepository.deleteById(id);
    }
}
