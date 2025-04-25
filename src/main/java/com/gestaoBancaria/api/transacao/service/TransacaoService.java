package com.gestaoBancaria.api.transacao.service;

import com.gestaoBancaria.api.transacao.dto.TransacaoRequestDTO;
import com.gestaoBancaria.api.transacao.enums.FormaPagamento;
import com.gestaoBancaria.api.transacao.expection.SaldoInsuficienteException;
import com.gestaoBancaria.api.conta.entity.Conta;
import com.gestaoBancaria.api.transacao.entity.Transacao;
import com.gestaoBancaria.api.conta.repository.ContaRepository;
import com.gestaoBancaria.api.transacao.repository.TransacaoRepository;
import com.gestaoBancaria.api.transacao.strategy.CreditoStrategy;
import com.gestaoBancaria.api.transacao.strategy.DebitoStrategy;
import com.gestaoBancaria.api.transacao.strategy.FormaPagamentoStrategy;
import com.gestaoBancaria.api.transacao.strategy.PixStrategy;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TransacaoService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final Map<FormaPagamento, FormaPagamentoStrategy> strategyMap = new HashMap<>();

    @Autowired
    public TransacaoService(
            ContaRepository contaRepository,
            TransacaoRepository transacaoRepository,
            List<FormaPagamentoStrategy> strategies) {

        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;

        for (FormaPagamentoStrategy strategy : strategies) {
            if (strategy instanceof PixStrategy) {
                strategyMap.put(FormaPagamento.P, strategy);
            } else if (strategy instanceof DebitoStrategy) {
                strategyMap.put(FormaPagamento.D, strategy);
            } else if (strategy instanceof CreditoStrategy) {
                strategyMap.put(FormaPagamento.C, strategy);
            }
        }
    }

    @Transactional
    public Conta realizarTransacao(TransacaoRequestDTO dto) {
        log.debug("Processando transação: conta={}, valor={}, formaPagamento={}",
                dto.getNumero_conta(), dto.getValor(), dto.getFormaPagamento());
        Conta conta = contaRepository.findByNumeroConta(dto.getNumero_conta())
                .orElseThrow(() -> {
                    log.warn("Conta {} não encontrada.", dto.getNumero_conta());
                    return new SaldoInsuficienteException("Conta não encontrada.");
                });

        FormaPagamentoStrategy strategy = strategyMap.get(dto.getFormaPagamento());
        if (strategy == null) {
            log.error("Forma de pagamento inválida: {}", dto.getFormaPagamento());
            throw new SaldoInsuficienteException("Forma de pagamento inválida.");
        }

        BigDecimal valorComTaxa = strategy.calcularValorFinal(dto.getValor());
        log.debug("Valor com taxa calculado: {}", valorComTaxa);

        if (conta.getSaldo().compareTo(valorComTaxa) < 0) {
            log.warn("Saldo insuficiente na conta {}. Saldo atual: {}, necessário: {}",
                    conta.getNumeroConta(), conta.getSaldo(), valorComTaxa);
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

        conta.setSaldo(conta.getSaldo().subtract(valorComTaxa));
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setFormaPagamento(dto.getFormaPagamento());
        transacao.setValor(dto.getValor());
        transacao.setConta(conta);
        transacaoRepository.save(transacao);

        log.info("Transação concluída: conta={}, novo saldo={}, tipo={}",
                conta.getNumeroConta(), conta.getSaldo(), dto.getFormaPagamento());
        return conta;
    }
}
