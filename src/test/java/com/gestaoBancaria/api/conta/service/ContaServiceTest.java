package com.gestaoBancaria.api.conta.service;

import com.gestaoBancaria.api.conta.dto.ContaRequestDTO;
import com.gestaoBancaria.api.conta.entity.Conta;
import com.gestaoBancaria.api.conta.expection.ContaNaoEncontradaException;
import com.gestaoBancaria.api.conta.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @Test
    void testCriarContaComSucesso() {
        ContaRequestDTO dto = new ContaRequestDTO();
        dto.setNumeroConta(123L);
        dto.setSaldo(BigDecimal.valueOf(100.00));

        Mockito.when(contaRepository.existsByNumeroConta(123L)).thenReturn(false);
        Mockito.when(contaRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Conta contaCriada = contaService.criarConta(dto);

        assertEquals(dto.getNumeroConta(), contaCriada.getNumeroConta());
        assertEquals(dto.getSaldo(), contaCriada.getSaldo());
    }

    @Test
    void testLancarExcecaoContaJaExiste() {
        ContaRequestDTO dto = new ContaRequestDTO();
        dto.setNumeroConta(123L);
        dto.setSaldo(BigDecimal.valueOf(100.00));

        Mockito.when(contaRepository.existsByNumeroConta(123L)).thenReturn(true);

        assertThrows(ContaNaoEncontradaException.class, () -> contaService.criarConta(dto));
    }

    @Test
    void testLancarExcecaoSaldoNegativo() {
        ContaRequestDTO dto = new ContaRequestDTO();
        dto.setNumeroConta(123L);
        dto.setSaldo(BigDecimal.valueOf(-10.00));

        assertThrows(ContaNaoEncontradaException.class, () -> contaService.criarConta(dto));
    }

    @Test
    void testBuscarContaComSucesso() {
        Conta conta = new Conta(123L, BigDecimal.valueOf(200));
        Mockito.when(contaRepository.findByNumeroConta(123L)).thenReturn(Optional.of(conta));

        Conta resultado = contaService.buscarConta(123L);

        assertNotNull(resultado);
        assertEquals(123L, resultado.getNumeroConta());
    }

    @Test
    void testLancarExcecaoContaNaoEncontrada() {
        Mockito.when(contaRepository.findByNumeroConta(999L)).thenReturn(Optional.empty());

        assertThrows(ContaNaoEncontradaException.class, () -> contaService.buscarConta(999L));
    }
}
