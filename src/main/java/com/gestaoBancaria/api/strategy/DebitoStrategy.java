package com.gestaoBancaria.api.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("D")
public class DebitoStrategy implements FormaPagamentoStrategy {
    public BigDecimal calcularValorFinal(BigDecimal valor) {
        return valor.multiply(BigDecimal.valueOf(1.03));
    }
}

