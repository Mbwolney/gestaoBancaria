package com.gestaoBancaria.api.transacao.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitoStrategyTest {

    private final DebitoStrategy debitoStrategy = new DebitoStrategy();

    @Test
    void testAplicarTaxaDe3PorcentoNoDebito() {
        BigDecimal valorOriginal = new BigDecimal("100.00");

        BigDecimal valorFinal = debitoStrategy.calcularValorFinal(valorOriginal);

        assertEquals(0, valorFinal.compareTo(new BigDecimal("103.00")));
    }
}
