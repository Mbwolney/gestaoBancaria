package com.gestaoBancaria.api.controllers;

import com.gestaoBancaria.api.dto.ContaResponseDTO;
import com.gestaoBancaria.api.dto.TransacaoRequestDTO;
import com.gestaoBancaria.api.model.Conta;
import com.gestaoBancaria.api.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> transacionar(@RequestBody TransacaoRequestDTO dto) {
        Conta contaAtualizada = transacaoService.realizarTransacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ContaResponseDTO(contaAtualizada));
    }

}
