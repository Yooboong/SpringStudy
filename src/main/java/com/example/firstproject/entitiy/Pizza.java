package com.example.firstproject.entitiy;

import com.example.firstproject.dto.PizzaDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String price;

    public static Pizza createEntity(PizzaDto dto) {
        return new Pizza(
                dto.getId(),
                dto.getName(),
                dto.getPrice()
        );
    }

    public void patch(PizzaDto dto) {
        if (dto.getName() != null)
            this.name = dto.getName();
        if (dto.getPrice() != null)
            this.price = dto.getPrice();
    }

}
