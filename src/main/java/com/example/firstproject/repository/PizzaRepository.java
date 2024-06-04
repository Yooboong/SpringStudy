package com.example.firstproject.repository;

import com.example.firstproject.entitiy.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

}
