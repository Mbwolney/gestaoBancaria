package com.gestaoBancaria.api.service;

import com.gestaoBancaria.api.dto.ContaRequestDTO;
import com.gestaoBancaria.api.model.Conta;
import com.gestaoBancaria.api.repositories.ContaRepository;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta criarConta(ContaRequestDTO dto) {
        if (contaRepository.existsByNumeroConta(dto.getNumeroConta())) {
            throw new RuntimeException("Conta já existente.");
        }

        Conta conta = new Conta();
        conta.setNumeroConta(dto.getNumeroConta());
        conta.setSaldo(dto.getSaldo());

        return contaRepository.save(conta);
    }

    public Conta buscarConta(Long numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));
    }

}
