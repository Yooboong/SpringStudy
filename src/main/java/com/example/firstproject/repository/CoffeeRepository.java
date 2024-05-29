package com.example.firstproject.repository;

import com.example.firstproject.entitiy.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {

}
