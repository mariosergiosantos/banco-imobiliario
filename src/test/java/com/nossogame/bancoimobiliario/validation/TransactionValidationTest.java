package com.nossogame.bancoimobiliario.validation;

import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.*;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.service.SalaService;
import com.nossogame.bancoimobiliario.service.validation.TransactionValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionValidationTest extends AbstractTest {

    @Mock
    private SalaService salaService;

    @InjectMocks
    private TransactionValidation transactionValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validarSaldo_DeveLancarExcecaoQuandoSaldoInsuficiente() {
        Jogador jogador = new Jogador();
        jogador.setSaldo(50.0);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                transactionValidation.validarSaldo(jogador, 100.0));

        assertEquals("Saldo insuficiente. Saldo atual: 50,00, valor necessário: 100,00", exception.getMessage());
    }

    @Test
    void validarSaldo_DevePassarQuandoSaldoSuficiente() {
        Jogador jogador = new Jogador();
        jogador.setSaldo(150.0);

        assertDoesNotThrow(() -> transactionValidation.validarSaldo(jogador, 100.0));
    }

    @Test
    void validarPropriedadeDisponivel_DeveLancarExcecaoQuandoPropriedadeJaPossuiComprador() {
        Propriedade propriedade = new Casa();
        propriedade.setDono(new Jogador());

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                transactionValidation.validarPropriedadeDisponivel(propriedade));

        assertEquals("Propriedade já pertence a um jogador.", exception.getMessage());
    }

    @Test
    void validarPropriedadeDisponivel_DevePassarQuandoPropriedadeNaoPossuiComprador() {
        Propriedade propriedade = new Companhia();
        propriedade.setDono(null);

        assertDoesNotThrow(() -> transactionValidation.validarPropriedadeDisponivel(propriedade));
    }

    @Test
    void validarPropriedadePertenceAoVendedor_DeveLancarExcecaoQuandoNaoPertence() {
        Jogador jogador = new Jogador();
        jogador.setId(UUID.randomUUID().toString());

        Propriedade propriedade = new Casa();
        propriedade.setDono(jogador);

        Jogador vendedor = new Jogador(); // Diferente do comprador
        vendedor.setId(UUID.randomUUID().toString());

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                transactionValidation.validarPropriedadePertenceAoVendedor(propriedade, vendedor));

        assertEquals("Propriedade não pertence ao vendedor.", exception.getMessage());
    }

    @Test
    void validarPropriedadePertenceAoVendedor_DevePassarQuandoPertence() {
        Jogador vendedor = new Jogador();
        vendedor.setId(UUID.randomUUID().toString());

        Propriedade propriedade = new Casa();
        propriedade.setDono(vendedor);

        assertDoesNotThrow(() -> transactionValidation.validarPropriedadePertenceAoVendedor(propriedade, vendedor));
    }

    @Test
    void validarSalaEmAndamento_DeveLancarExcecaoQuandoSalaNaoEmAndamento() throws ResourceNotFoundException {
        Sala sala = new Sala();
        sala.setStatus(StatusSala.ABERTA);

        when(salaService.buscarSalaPorId("salaId")).thenReturn(sala);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                transactionValidation.validarSalaEmAndamento("salaId"));

        assertEquals("Sala não está disponível para realizar transações.", exception.getMessage());
        verify(salaService, times(1)).buscarSalaPorId("salaId");
    }

    @Test
    void validarSalaEmAndamento_DevePassarQuandoSalaEmAndamento() throws ResourceNotFoundException {
        Sala sala = new Sala();
        sala.setStatus(StatusSala.EM_ANDAMENTO);

        when(salaService.buscarSalaPorId("salaId")).thenReturn(sala);

        assertDoesNotThrow(() -> transactionValidation.validarSalaEmAndamento("salaId"));
        verify(salaService, times(1)).buscarSalaPorId("salaId");
    }
}
