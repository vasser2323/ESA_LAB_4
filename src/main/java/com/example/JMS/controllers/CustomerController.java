package com.example.JMS.controllers;

import com.example.JMS.Services.CustomerService;
import com.example.JMS.jms.Producer;
import com.example.JMS.models.Customer;
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
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private Producer producer;

    @GetMapping("/customer")
    public @ResponseBody Iterable<Customer> getCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/customer/{id}")
    public @ResponseBody Optional<Customer> getCustomer(@PathVariable int id) {
        return customerService.findById(id);
    }

    @PostMapping("/customer")
    public @ResponseBody int createCustomer(@RequestBody Customer customer) {
        if (customer == null) {
            return Response.SC_NOT_FOUND;
        }
        var cust = customerService.saveCustomer(customer);
        System.out.println(cust.getCustomerId());

        producer.sendEvent(cust,
                EventType.CREATE_CUSTOMER,
                cust.getCustomerId()
        );

        return Response.SC_OK;
    }

    @DeleteMapping("/customer/{id}")
    public @ResponseBody int deleteCustomer(@PathVariable int id) {
        if (customerService.findById(id).isEmpty()) {
            return Response.SC_NOT_FOUND;
        }
        producer.sendEvent(customerService.findById(id), EventType.DELETE_CUSTOMER, id);
        customerService.findById(id).get().getCreditCards().forEach(
                creditCard -> {
                    producer.sendEvent(creditCard, EventType.DELETE_CARD, creditCard.getCardId());
                }
        );

        customerService.deleteById(id);
        return Response.SC_OK;
    }

    @PutMapping("/customer/{id}")
    public @ResponseBody int updateCustomer(@RequestBody Customer newCustomer, @PathVariable Integer id) {
        return customerService.findById(id)
                .map(customer -> {
                    if (newCustomer.getCustomerName() != null) {
                        customer.setCustomerName(newCustomer.getCustomerName());
                    }
                    if (newCustomer.getAddress() != null) {
                        customer.setAddress(newCustomer.getAddress());
                    }
                    customerService.saveCustomer(customer);
                    return Response.SC_OK;
                })
                .orElseGet(() -> {
                    return Response.SC_NOT_FOUND;
                });
    }

    @GetMapping(path = "xsl/customer")
    public ModelAndView getStudent() throws JsonProcessingException {
        Iterable<Customer> list = customerService.findAll();
        return getModelAndView(list, "customerXSL");
    }
}
