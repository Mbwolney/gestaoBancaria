package com.gestaoBancaria.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaoBancaria.api.conta.controller.ContaController;
import com.gestaoBancaria.api.conta.dto.ContaRequestDTO;
import com.gestaoBancaria.api.conta.expection.ContaNaoEncontradaException;
import com.gestaoBancaria.api.conta.service.ContaService;
import com.gestaoBancaria.api.transacao.expection.SaldoInsuficienteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ContaController.class)
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaService contaService;

    @Test
    void testRetornar404QuandoContaNaoEncontrada() throws Exception {
        when(contaService.buscarConta(999L))
                .thenThrow(new ContaNaoEncontradaException("Conta não encontrada."));

        mockMvc.perform(get("/conta")
                        .param("numeroConta", "999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Conta não encontrada."));
    }

    @Test
    void testRetornar404QuandoSaldoInsuficiente() throws Exception {
        ContaRequestDTO dto = new ContaRequestDTO();
        dto.setNumeroConta(123L);
        dto.setSaldo(new BigDecimal("500.00"));

        when(contaService.criarConta(any()))
                .thenThrow(new SaldoInsuficienteException("Saldo insuficiente."));

        mockMvc.perform(post("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Saldo insuficiente."));
    }

    @Test
    void testRetornar400QuandoIllegalArgument() throws Exception {
        when(contaService.buscarConta(any()))
                .thenThrow(new IllegalArgumentException("Formato inválido"));

        mockMvc.perform(get("/conta")
                        .param("numeroConta", "123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Formato inválido"));
    }

    @Test
    void testRetornar500QuandoErroGenerico() throws Exception {
        when(contaService.buscarConta(any()))
                .thenThrow(new RuntimeException("Falha inesperada"));

        mockMvc.perform(get("/conta")
                        .param("numeroConta", "123"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Erro inesperado: Falha inesperada"));
    }
}

