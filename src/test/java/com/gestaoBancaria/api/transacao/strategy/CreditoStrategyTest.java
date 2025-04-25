package com.gestaoBancaria.api.transacao.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditoStrategyTest {

    private final CreditoStrategy creditoStrategy = new CreditoStrategy();

    @Test
    void testAplicarTaxaDe5PorcentoNoCredito() {
        BigDecimal valorOriginal = new BigDecimal("100.00");

        BigDecimal valorFinal = creditoStrategy.calcularValorFinal(valorOriginal);

        assertEquals(0, valorFinal.compareTo(new BigDecimal("105.00")));
    }
}
