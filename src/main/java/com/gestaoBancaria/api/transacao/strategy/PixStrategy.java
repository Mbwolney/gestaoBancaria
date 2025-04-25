package com.gestaoBancaria.api.transacao.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("P")
public class PixStrategy implements FormaPagamentoStrategy {
    public BigDecimal calcularValorFinal(BigDecimal valor) {
        return valor;
    }
}

