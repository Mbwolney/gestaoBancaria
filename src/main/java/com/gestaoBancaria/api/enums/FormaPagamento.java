package com.gestaoBancaria.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FormaPagamento {
    P,
    C,
    D;

    @JsonCreator
    public static FormaPagamento fromString(String value) {
        try {
            return FormaPagamento.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Forma de pagamento inválida. Valores aceitos: P, C, D.");
        }
    }
}
