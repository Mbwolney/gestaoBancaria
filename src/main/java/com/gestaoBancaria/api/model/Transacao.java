package com.gestaoBancaria.api.model;

import com.gestaoBancaria.api.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "transacao")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false, length = 1)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "conta", nullable = false)
    private Conta conta;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;
}

