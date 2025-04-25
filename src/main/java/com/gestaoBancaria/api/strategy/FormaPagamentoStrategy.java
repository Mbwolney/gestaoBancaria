package com.gestaoBancaria.api.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public interface FormaPagamentoStrategy {
    BigDecimal calcularValorFinal(BigDecimal valor);
}
