package com.gestaoBancaria.api.conta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaoBancaria.api.conta.dto.ContaRequestDTO;
import com.gestaoBancaria.api.conta.entity.Conta;
import com.gestaoBancaria.api.conta.service.ContaService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@WebMvcTest(controllers = ContaController.class)
public class ContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaService contaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCriarContaComSucesso() throws Exception {
        ContaRequestDTO requestDTO = new ContaRequestDTO();
        requestDTO.setNumeroConta(123L);
        requestDTO.setSaldo(new BigDecimal("100.00"));

        Conta conta = new Conta(123L, new BigDecimal("100.00"));
        given(contaService.criarConta(any())).willReturn(conta);

        mockMvc.perform(post("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero_conta").value(123))
                .andExpect(jsonPath("$.saldo").value(100.00));
    }

    @Test
    void testBuscarContaComSucesso() throws Exception {
        Conta conta = new Conta(123L, new BigDecimal("150.00"));
        given(contaService.buscarConta(123L)).willReturn(conta);

        mockMvc.perform(get("/conta")
                        .param("numeroConta", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero_conta").value(123))
                .andExpect(jsonPath("$.saldo").value(150.00));
    }
}
