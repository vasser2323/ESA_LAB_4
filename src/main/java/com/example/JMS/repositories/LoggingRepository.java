package com.example.JMS.repositories;

import com.example.JMS.models.Logging;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends CrudRepository<Logging, Integer> {
}
