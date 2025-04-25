package com.gestaoBancaria.api.transacao.service;

import com.gestaoBancaria.api.conta.entity.Conta;
import com.gestaoBancaria.api.conta.repository.ContaRepository;
import com.gestaoBancaria.api.transacao.dto.TransacaoRequestDTO;
import com.gestaoBancaria.api.transacao.enums.FormaPagamento;
import com.gestaoBancaria.api.transacao.repository.TransacaoRepository;
import com.gestaoBancaria.api.transacao.strategy.CreditoStrategy;
import com.gestaoBancaria.api.transacao.strategy.DebitoStrategy;
import com.gestaoBancaria.api.transacao.strategy.FormaPagamentoStrategy;
import com.gestaoBancaria.api.transacao.strategy.PixStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        List<FormaPagamentoStrategy> strategies = List.of(
                new PixStrategy(), new DebitoStrategy(), new CreditoStrategy()
        );
        transacaoService = new TransacaoService(contaRepository, transacaoRepository, strategies);
    }

    @Test
    void testRealizarTransacaoDebitoComTaxa() {
        Conta contaMock = new Conta(123L, new BigDecimal("200.00"));
        when(contaRepository.findByNumeroConta(123L)).thenReturn(Optional.of(contaMock));

        TransacaoRequestDTO dto = new TransacaoRequestDTO();
        dto.setNumero_conta(123L);
        dto.setValor(new BigDecimal("100.00"));
        dto.setFormaPagamento(FormaPagamento.D);

        Conta resultado = transacaoService.realizarTransacao(dto);

        assertEquals(0, resultado.getSaldo().compareTo(new BigDecimal("97.00")));
    }


    @Test
    void testRealizarTransacaoCreditoComTaxa() {
        Conta contaMock = new Conta(123L, new BigDecimal("200.00"));
        given(contaRepository.findByNumeroConta(123L)).willReturn(Optional.of(contaMock));

        TransacaoRequestDTO dto = new TransacaoRequestDTO();
        dto.setNumero_conta(123L);
        dto.setValor(new BigDecimal("100.00"));
        dto.setFormaPagamento(FormaPagamento.C);

        Conta resultado = transacaoService.realizarTransacao(dto);

        assertEquals(0, resultado.getSaldo().compareTo(new BigDecimal("95.00")));
    }

    @Test
    void testRealizarTransacaoPixSemTaxa() {
        Conta contaMock = new Conta(123L, new BigDecimal("200.00"));
        given(contaRepository.findByNumeroConta(123L)).willReturn(Optional.of(contaMock));

        TransacaoRequestDTO dto = new TransacaoRequestDTO();
        dto.setNumero_conta(123L);
        dto.setValor(new BigDecimal("100.00"));
        dto.setFormaPagamento(FormaPagamento.P);

        Conta resultado = transacaoService.realizarTransacao(dto);

        assertEquals(new BigDecimal("100.00"), resultado.getSaldo());
    }
}

