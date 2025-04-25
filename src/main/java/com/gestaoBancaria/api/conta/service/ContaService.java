package com.gestaoBancaria.api.conta.service;

import com.gestaoBancaria.api.conta.dto.ContaRequestDTO;
import com.gestaoBancaria.api.conta.expection.ContaNaoEncontradaException;
import com.gestaoBancaria.api.conta.entity.Conta;
import com.gestaoBancaria.api.conta.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta criarConta(ContaRequestDTO dto) {
        if (contaRepository.existsByNumeroConta(dto.getNumeroConta())) {
            throw new ContaNaoEncontradaException("Conta já existente.");
        }

        if (dto.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new ContaNaoEncontradaException("O saldo inicial não pode ser negativo.");
        }

        Conta conta = new Conta();
        conta.setNumeroConta(dto.getNumeroConta());
        conta.setSaldo(dto.getSaldo());

        return contaRepository.save(conta);
    }

    public Conta buscarConta(Long numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada."));
    }

}
