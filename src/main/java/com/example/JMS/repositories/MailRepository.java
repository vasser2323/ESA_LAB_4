package com.example.JMS.repositories;

import com.example.JMS.models.Customer;
import com.example.JMS.models.Mail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends CrudRepository<Mail, Integer> {
}
