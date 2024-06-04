package com.example.firstproject.service;

import com.example.firstproject.dto.PizzaDto;
import com.example.firstproject.entitiy.Pizza;
import com.example.firstproject.repository.PizzaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Transactional
    public PizzaDto create(PizzaDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("id는 입력하면 안돼요");
        }

        Pizza entity = Pizza.createEntity(dto);
        Pizza created = pizzaRepository.save(entity);
        return PizzaDto.createDto(created);
    }

    public List<PizzaDto> readAll() {
        List<PizzaDto> dtoList = pizzaRepository.findAll()
                .stream()
                .map(entity -> PizzaDto.createDto(entity))
                .collect(Collectors.toList());

        return dtoList;
    }


    public PizzaDto read(Long id) {
        Pizza entity = pizzaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id에 해당하는 데이터가 없습니다")
        );

        return PizzaDto.createDto(entity);
    }

    @Transactional
    public PizzaDto update(Long id, PizzaDto dto) {
        Pizza target = pizzaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id에 해당하는 데이터가 없습니다")
        );

        //JSON 입력값 중 id가 들어왔으면 오류
        if (dto.getId() != null)
            throw new IllegalArgumentException("id 값을 입력하면 안돼요");

        target.patch(dto);
        Pizza updated = pizzaRepository.save(target);
        return PizzaDto.createDto(updated);
    }

    @Transactional
    public PizzaDto delete(Long id) {
        Pizza target = pizzaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id에 해당하는 데이터가 없습니다")
        );

        pizzaRepository.delete(target);
        return PizzaDto.createDto(target);
    }

}
