package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.VencedorJogoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.service.JogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JogoControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JogoService jogoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveIniciarJogoComSucesso() throws Exception {
        doNothing().when(jogoService).iniciar(codigoSala);

        mockMvc.perform(post("/api/v1/salas/" + codigoSala + "/iniciar"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarErroQuandoNaoForPossivelIniciarJogo() throws Exception {

        doThrow(new RegraNegocialException("Necessário ter ao menos 2 jogadores"))
                .when(jogoService).iniciar(codigoSala);

        mockMvc.perform(post("/api/v1/salas/" + codigoSala + "/iniciar"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Necessário ter ao menos 2 jogadores"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(header().exists("X-Correlation-Id"));
    }

    @Test
    void deveEncerrarJogoComSucesso() throws Exception {
        when(jogoService.finalizar(codigoSala)).thenReturn(new VencedorJogoDto(sala.getId(), "Jogador 1", 1000, 1));

        mockMvc.perform(post("/api/v1/salas/" + codigoSala + "/finalizar"))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Correlation-Id"));
    }

    @Test
    void deveRetornarErroQuandoNaoForPossivelEncerrarJogo() throws Exception {

        doThrow(new RegraNegocialException("Status da sala não permite finalizar jogo"))
                .when(jogoService).finalizar(codigoSala);

        mockMvc.perform(post("/api/v1/salas/" + codigoSala + "/finalizar"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Status da sala não permite finalizar jogo"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(header().exists("X-Correlation-Id"));
    }
}

