package com.example.demo.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    private Long id;
}
