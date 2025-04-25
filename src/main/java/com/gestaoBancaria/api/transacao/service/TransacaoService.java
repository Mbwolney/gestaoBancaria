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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Conta conta = contaRepository.findByNumeroConta(dto.getNumero_conta())
                .orElseThrow(() -> new SaldoInsuficienteException("Conta não encontrada."));

        FormaPagamentoStrategy strategy = strategyMap.get(dto.getFormaPagamento());
        if (strategy == null) {
            throw new SaldoInsuficienteException("Forma de pagamento inválida.");
        }

        BigDecimal valorComTaxa = strategy.calcularValorFinal(dto.getValor());

        if (conta.getSaldo().compareTo(valorComTaxa) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

        conta.setSaldo(conta.getSaldo().subtract(valorComTaxa));
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setFormaPagamento(dto.getFormaPagamento());
        transacao.setValor(dto.getValor());
        transacao.setConta(conta);
        transacaoRepository.save(transacao);

        return conta;
    }
}
