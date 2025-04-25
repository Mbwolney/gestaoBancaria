package com.gestaoBancaria.api.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestaoBancaria.api.transacao.enums.FormaPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransacaoRequestDTO {

    @JsonProperty("forma_pagamento")
    private FormaPagamento formaPagamento;

    @JsonProperty("numero_conta")
    private Long numero_conta;

    private BigDecimal valor;
}
