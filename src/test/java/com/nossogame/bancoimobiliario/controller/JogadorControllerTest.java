package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.JogadorDto;
import com.nossogame.bancoimobiliario.dto.request.JogadorRequestDto;
import com.nossogame.bancoimobiliario.service.JogadorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class JogadorControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JogadorService jogadorService;

    @Test
    void deveAdicionarJogadorComSucesso() throws Exception {
        JogadorDto jogadorDto = new JogadorDto("123", "Jogador A", 1500d, false);

        when(jogadorService.adicionarJogador(eq("ABCDE1"), any(JogadorRequestDto.class))).thenReturn(jogadorDto);

        mockMvc.perform(post("/api/v1/jogadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "nome": "Jogador A",
                                        "salaId": "ER84T2R"
                                    }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void deveRetornarErroQuandoDadosInvalidos() throws Exception {
        mockMvc.perform(post("/api/v1/jogadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "nomew": ""
                                    }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveListarJogadoresDeUmaSala() throws Exception {
        JogadorDto jogadorA = new JogadorDto("123", "Jogador A", 1500d, false);

        JogadorDto jogadorB = new JogadorDto("124", "Jogador B", 1500d, false);


        List<JogadorDto> jogadores = List.of(
                jogadorA,
                jogadorB
        );

        when(jogadorService.listarJogadoresDaSala("ABCDE1")).thenReturn(jogadores);

        mockMvc.perform(get("/api/v1/jogadores/salas/ABCDE1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].nome").value("Jogador A"))
                .andExpect(jsonPath("$[1].id").value("124"))
                .andExpect(jsonPath("$[1].nome").value("Jogador B"));
    }
}