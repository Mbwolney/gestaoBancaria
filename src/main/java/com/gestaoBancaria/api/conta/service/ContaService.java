package com.gestaoBancaria.api.conta.service;

import com.gestaoBancaria.api.conta.dto.ContaRequestDTO;
import com.gestaoBancaria.api.conta.expection.ContaNaoEncontradaException;
import com.gestaoBancaria.api.conta.entity.Conta;
import com.gestaoBancaria.api.conta.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta criarConta(ContaRequestDTO dto) {
        log.debug("Criando conta {}", dto.getNumeroConta());
        if (contaRepository.existsByNumeroConta(dto.getNumeroConta())) {
            log.warn("Tentativa de criar conta que já existe: {}", dto.getNumeroConta());
            throw new ContaNaoEncontradaException("Conta já existente.");
        }

        if (dto.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new ContaNaoEncontradaException("O saldo inicial não pode ser negativo.");
        }

        Conta conta = new Conta();
        conta.setNumeroConta(dto.getNumeroConta());
        conta.setSaldo(dto.getSaldo());

        log.info("Conta {} criada com sucesso com saldo {}", conta.getNumeroConta(), conta.getSaldo());
        return contaRepository.save(conta);
    }

    public Conta buscarConta(Long numeroConta) {
        log.debug("Buscando conta com número {}", numeroConta);
        return contaRepository.findByNumeroConta(numeroConta)
                .map(conta -> {
                    log.info("Conta encontrada: número={} saldo={}", conta.getNumeroConta(), conta.getSaldo());
                    return conta;
                })
                .orElseThrow(() -> {
                    log.error("Conta não encontrada: número={}", numeroConta);
                    return new ContaNaoEncontradaException("Conta não encontrada.");
                });
    }
}
