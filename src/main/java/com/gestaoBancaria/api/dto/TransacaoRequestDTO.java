package com.gestaoBancaria.api.dto;

import com.gestaoBancaria.api.enums.FormaPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransacaoRequestDTO {
    private FormaPagamento formaPagamento;
    private Long numeroConta;
    private BigDecimal valor;
}
