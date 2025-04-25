package com.gestaoBancaria.api.conta.controller;

import com.gestaoBancaria.api.conta.dto.ContaRequestDTO;
import com.gestaoBancaria.api.conta.dto.ContaResponseDTO;
import com.gestaoBancaria.api.conta.entity.Conta;
import com.gestaoBancaria.api.conta.service.ContaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody @Valid ContaRequestDTO dto) {
        log.debug("Iniciando criação de conta com número {}", dto.getNumeroConta());
        Conta novaConta = contaService.criarConta(dto);
        log.info("Conta criada com sucesso: número={} saldo={}", novaConta.getNumeroConta(), novaConta.getSaldo());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ContaResponseDTO(novaConta));
    }

    @GetMapping
    public ResponseEntity<ContaResponseDTO> buscarConta(@RequestParam Long numeroConta) {
        log.debug("Iniciando busca de conta com número {}", numeroConta);
        Conta conta = contaService.buscarConta(numeroConta);
        log.info("Conta encontrada: número={} saldo={}", conta.getNumeroConta(), conta.getSaldo());
        return ResponseEntity.ok(new ContaResponseDTO(conta));
    }

}
