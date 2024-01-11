package com.example.JMS.controllers;

import com.example.JMS.Services.CreditCardService;
import com.example.JMS.Services.CustomerService;
import com.example.JMS.jms.Producer;
import com.example.JMS.models.CreditCard;
import com.example.JMS.models.EventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static com.example.JMS.utils.xslTransform.getModelAndView;


@RestController
@RequestMapping(
        value = "api",
        produces = {"application/json", "application/xml"}
)
public class CreditController {
    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private Producer producer;

    @GetMapping("/credit")
    public @ResponseBody Iterable<CreditCard> getCreditCards() {
        return creditCardService.getAllCreditCards();
    }

    @GetMapping("/credit/{id}")
    public @ResponseBody Optional<CreditCard> getCreditCard(@PathVariable Integer id) {
        return creditCardService.getCreditCardById(id);
    }

    @PostMapping("/credit")
    public @ResponseBody int createCreditCard(@RequestBody CreditCard creditCard) {
        if (creditCard == null) {
            return Response.SC_NOT_FOUND;
        }
        var credit = creditCardService.createOrUpdateCreditCard(creditCard);

        producer.sendEvent(
                credit,
                EventType.CREATE_CARD,
                credit.getCardId()
        );

        return Response.SC_OK;
    }

    @Autowired
    CustomerService customerService;

    @DeleteMapping("/credit/{id}")
    public @ResponseBody int deleteCreditCard(@PathVariable Integer id) {
        if (creditCardService.getCreditCardById(id).isEmpty()) {
            return Response.SC_NOT_FOUND;
        }
        customerService.findAll().forEach(
                customer -> {
                    customer.getCreditCards().forEach(
                            creditCard -> {
                                if (creditCard.getCardId().equals(id)) {
                                    customer.getCreditCards().remove(creditCard);
                                    customerService.saveCustomer(customer);
                                    return;
                                }
                            });
                }
        );
        var credit = creditCardService.getCreditCardById(id);
        producer.sendEvent(
                credit,
                EventType.DELETE_CARD,
                credit.get().getCardId()
        );

        creditCardService.deleteCreditCardById(id);
        return Response.SC_OK;
    }

    @PutMapping("/credit/{id}")
    public @ResponseBody int updateCreditCard(@RequestBody CreditCard newCredit, @PathVariable Integer id) {
        var credit_ = creditCardService.getCreditCardById(id);
        if (credit_.isEmpty()) {
            return Response.SC_NOT_FOUND;
        }
        var credit = credit_.get();
        credit.setCardNumber(newCredit.getCardNumber());
        credit.setCardHolderName(newCredit.getCardHolderName());
        credit.setCvv(newCredit.getCvv());
        credit.setExpirationDate(newCredit.getExpirationDate());

        return Response.SC_OK;
    }

    @GetMapping(path = "xsl/creditCard")
    public ModelAndView getCreditCard() throws JsonProcessingException {
        Iterable<CreditCard> list = creditCardService.getAllCreditCards();
        return getModelAndView(list, "creditCardXSL");
    }
}