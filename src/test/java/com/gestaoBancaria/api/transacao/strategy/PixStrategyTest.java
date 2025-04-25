package com.gestaoBancaria.api.transacao.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PixStrategyTest {

    private PixStrategy pixStrategy = new PixStrategy();

    @Test
    void testAplicarTaxaDe3PorcentoNoDebito() {
        BigDecimal valorOriginal = new BigDecimal("100.00");

        BigDecimal valorFinal = pixStrategy.calcularValorFinal(valorOriginal);

        assertEquals(0, valorFinal.compareTo(new BigDecimal("100.00")));
    }
}
