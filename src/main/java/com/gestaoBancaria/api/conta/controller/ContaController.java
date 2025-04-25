package com.gestaoBancaria.api.conta.controller;

import com.gestaoBancaria.api.conta.dto.ContaRequestDTO;
import com.gestaoBancaria.api.conta.dto.ContaResponseDTO;
import com.gestaoBancaria.api.conta.entity.Conta;
import com.gestaoBancaria.api.conta.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody @Valid ContaRequestDTO dto) {
        Conta novaConta = contaService.criarConta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ContaResponseDTO(novaConta));
    }

    @GetMapping
    public ResponseEntity<ContaResponseDTO> buscarConta(@RequestParam Long numeroConta) {
        Conta conta = contaService.buscarConta(numeroConta);
        return ResponseEntity.ok(new ContaResponseDTO(conta));
    }

}
