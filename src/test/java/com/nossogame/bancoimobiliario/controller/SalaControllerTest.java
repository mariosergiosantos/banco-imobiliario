package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.SalaDto;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.service.SalaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SalaController.class)
class SalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaService salaService;

    @Test
    void deveCriarSalaComSucesso() throws Exception {
        // Dados simulados
        SalaDto salaDto = new SalaDto();
        salaDto.setId("ABCDE1");
        salaDto.setDataCriacao(LocalDateTime.now());
        salaDto.setStatus(StatusSala.ABERTA);

        when(salaService.criarSala()).thenReturn(salaDto);

        // Requisição simulada
        mockMvc.perform(post("/api/v1/salas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "dataCriacao": "2025-01-01T10:00:00",
                                      "status": "ABERTA"
                                    }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ABCDE1"))
                .andExpect(jsonPath("$.status").value("ABERTA"));
    }

    @Test
    void deveRetornarErroQuandoDadosInvalidos() throws Exception {
        // Requisição com dados inválidos
        mockMvc.perform(post("/api/v1/salas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "dataCriacao": null,
                                      "status": "ABERTA"
                                    }
                                """))
                .andExpect(status().isBadRequest());
    }
}

