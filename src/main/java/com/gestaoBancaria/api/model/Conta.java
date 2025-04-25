package com.gestaoBancaria.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

import java.math.BigDecimal;

@Table(name = "conta")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conta {

    @Id
    @Column(name = "numero_conta")
    private Long numeroConta;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

}
