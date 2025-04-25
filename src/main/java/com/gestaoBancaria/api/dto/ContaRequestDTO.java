package com.gestaoBancaria.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ContaRequestDTO {
    private Long numeroConta;
    private BigDecimal saldo;
}
