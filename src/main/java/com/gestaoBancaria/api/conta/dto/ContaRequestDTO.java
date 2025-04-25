package com.gestaoBancaria.api.conta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ContaRequestDTO {

    @NotNull(message = "O número da conta é obrigatório.")
    @JsonProperty("numero_conta")
    private Long numeroConta;

    @DecimalMin(value = "0.00", inclusive = true, message = "O saldo inicial não pode ser negativo.")
    private BigDecimal saldo;
}
