package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.VencedorJogoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.service.JogoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(JogoController.class)
class JogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JogoService jogoService;

    @Test
    void deveIniciarJogoComSucesso() throws Exception {
        doNothing().when(jogoService).iniciar("ABCDE1");

        mockMvc.perform(patch("/api/v1/salas/ABCDE1/iniciar"))
                .andExpect(status().isOk())
                .andExpect(content().string("Jogo iniciado com sucesso!"));
    }

    @Test
    void deveRetornarErroQuandoNaoForPossivelIniciarJogo() throws Exception {
        doThrow(new RegraNegocialException("Não é possível iniciar o jogo sem jogadores."))
                .when(jogoService).iniciar("ABCDE1");

        mockMvc.perform(patch("/api/v1/salas/ABCDE1/iniciar"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Não é possível iniciar o jogo sem jogadores."));
    }

    @Test
    void deveEncerrarJogoComSucesso() throws Exception {
        when(jogoService.finalizar("ABCDE1")).thenReturn(new VencedorJogoDto());

        mockMvc.perform(patch("/api/v1/salas/ABCDE1/encerrar"))
                .andExpect(status().isOk())
                .andExpect(content().string("Jogo encerrado com sucesso!"));
    }

    @Test
    void deveRetornarErroQuandoNaoForPossivelEncerrarJogo() throws Exception {
        doThrow(new RegraNegocialException("O jogo ainda não está em andamento."))
                .when(jogoService).finalizar("ABCDE1");

        mockMvc.perform(patch("/api/v1/salas/ABCDE1/encerrar"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O jogo ainda não está em andamento."));
    }
}

