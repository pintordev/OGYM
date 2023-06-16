package com.ogym.project.trainer.field;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    public Field create(String name) {
        Field field = new Field();
        field.setName(name);
        this.fieldRepository.save(field);
        return field;
    }

    public List<Field> getList() {
        return this.fieldRepository.findByOrderByIdAsc();
    }

    public Field getField(String name) {
        return this.fieldRepository.findByName(name);
    }
}