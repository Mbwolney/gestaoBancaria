package com.gestaoBancaria.api.conta.dto;

import com.gestaoBancaria.api.conta.entity.Conta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ContaResponseDTO {
    private Long numeroConta;
    private BigDecimal saldo;

    public ContaResponseDTO(Conta conta) {
        this.numeroConta = conta.getNumeroConta();
        this.saldo = conta.getSaldo();
    }
}
