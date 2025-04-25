package com.gestaoBancaria.api.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("C")
public class CreditoStrategy implements FormaPagamentoStrategy {
    public BigDecimal calcularValorFinal(BigDecimal valor) {
        return valor.multiply(BigDecimal.valueOf(1.05));
    }
}
