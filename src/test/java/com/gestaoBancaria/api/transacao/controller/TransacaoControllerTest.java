package com.gestaoBancaria.api.transacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaoBancaria.api.conta.entity.Conta;

import com.gestaoBancaria.api.transacao.dto.TransacaoRequestDTO;
import com.gestaoBancaria.api.transacao.enums.FormaPagamento;
import com.gestaoBancaria.api.transacao.service.TransacaoService;
import com.gestaoBancaria.api.transacao.service.TransacaoServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@WebMvcTest(controllers = TransacaoController.class)
public class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransacaoService transacaoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testRealizarTransacao() throws Exception {
        TransacaoRequestDTO requestDTO = new TransacaoRequestDTO();
        requestDTO.setNumero_conta(123L);
        requestDTO.setValor(new BigDecimal("50.00"));
        requestDTO.setFormaPagamento(FormaPagamento.D);

        Conta contaAtualizada = new Conta(123L, new BigDecimal("150.00"));

        given(transacaoService.realizarTransacao(any())).willReturn(contaAtualizada);

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero_conta").value(123))
                .andExpect(jsonPath("$.saldo").value(150.00));
    }
}
