package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entitiy.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CoffeeApiController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("/api/coffees")
    public Iterable<Coffee> index() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> show(@PathVariable("id") Long id) {
        Coffee coffee = coffeeRepository.findById(id).orElse(null);
        return (coffee != null) ?
                ResponseEntity.status(HttpStatus.OK).body(coffee) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/api/coffees")
    public ResponseEntity<Coffee> create(@RequestBody CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        if (coffee.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Coffee created = coffeeRepository.save(coffee);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable("id") Long id, @RequestBody CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        log.info("id: {}, coffee: {}", id, coffee.toString());

        Coffee target = coffeeRepository.findById(id).orElse(null);

        if (target == null || id != coffee.getId()) {
            log.info("잘못된 요청! id: {}, coffee: {}", id, coffee.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable("id") Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);

        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}