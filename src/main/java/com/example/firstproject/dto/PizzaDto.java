package com.example.firstproject.dto;

import com.example.firstproject.entitiy.Pizza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class PizzaDto {

    private Long id;

    private String name;

    private String price;

    public static PizzaDto createDto(Pizza entity) {
        return new PizzaDto(
                entity.getId(),
                entity.getName(),
                entity.getPrice()
        );
    }

}
