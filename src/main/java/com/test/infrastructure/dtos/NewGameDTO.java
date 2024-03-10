package com.test.infrastructure.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NewGameDTO(String name, LocalDate releaseDate, BigDecimal price) {
}
