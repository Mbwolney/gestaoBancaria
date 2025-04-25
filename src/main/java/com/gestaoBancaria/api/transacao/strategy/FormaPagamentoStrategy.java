package com.gestaoBancaria.api.transacao.strategy;

import java.math.BigDecimal;

public interface FormaPagamentoStrategy {
    BigDecimal calcularValorFinal(BigDecimal valor);
}
