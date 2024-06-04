package com.example.firstproject.api;

import com.example.firstproject.dto.PizzaDto;
import com.example.firstproject.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PizzaApiController {

    @Autowired
    private PizzaService pizzaService;

    @PostMapping("/api/pizzas") //등록
    public ResponseEntity<PizzaDto> create(@RequestBody PizzaDto dto) {
        PizzaDto createdDto = pizzaService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
    }

    @GetMapping("/api/pizzas") //전체 조회
    public ResponseEntity<List<PizzaDto>> readAll() {
        List<PizzaDto> dtoList = pizzaService.readAll();

        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    @GetMapping("/api/pizzas/{id}") //단일 조회
    public ResponseEntity<PizzaDto> read(@PathVariable("id") Long id) {
        PizzaDto dto = pizzaService.read(id);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PatchMapping("/api/pizzas/{id}") //수정
    public ResponseEntity<PizzaDto> update(@PathVariable("id") Long id, @RequestBody PizzaDto dto) {
        PizzaDto updatedDto = pizzaService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @DeleteMapping("/api/pizzas/{id}") //삭제
    public ResponseEntity<PizzaDto> delete(@PathVariable("id") Long id) {
        PizzaDto deletedDto = pizzaService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }

}
