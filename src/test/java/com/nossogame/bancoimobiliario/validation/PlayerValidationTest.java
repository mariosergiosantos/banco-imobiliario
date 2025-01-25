package com.nossogame.bancoimobiliario.validation;

import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.service.validation.PlayerValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerValidationTest extends AbstractTest {

    @InjectMocks
    private PlayerValidation playerValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validarJogador_DeveLancarExcecaoQuandoSalaNaoEstaAberta() {
        Sala sala = new Sala();
        sala.setStatus(StatusSala.ENCERRADA);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                playerValidation.validateCreatePlayer(sala));

        assertEquals("Sala não está aberta para entrada de novos jogadores", exception.getMessage());
    }

    @Test
    public void validarJogador_DeveLancarExcecaoSalaCheia() {
        Sala sala = new Sala();
        sala.setStatus(StatusSala.ABERTA);
        sala.setJogadores(Arrays.asList(new Jogador(), new Jogador(), new Jogador(), new Jogador(), new Jogador(),
                new Jogador(), new Jogador(), new Jogador()));

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                playerValidation.validateCreatePlayer(sala));

        assertEquals("Sala já preenhida com maximo de jogadores", exception.getMessage());
    }
}
