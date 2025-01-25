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
        JogadorDto jogadorDto = new JogadorDto();
        jogadorDto.setId("123");
        jogadorDto.setNome("Jogador A");
        jogadorDto.setSaldo(1500);

        when(jogadorService.adicionarJogador(eq("ABCDE1"), any(JogadorRequestDto.class))).thenReturn(jogadorDto);

        // /api/v1/salas/ABCDE1/jogadores
        mockMvc.perform(post("/api/v1/jogadores/salas/ABCDE1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "nome": "Jogador A"
                                    }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.nome").value("Jogador A"))
                .andExpect(jsonPath("$.saldo").value(1500));
    }

    @Test
    void deveRetornarErroQuandoDadosInvalidos() throws Exception {
        mockMvc.perform(post("/api/v1/jogadores/salas/ABCDE1")
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
        JogadorDto jogadorA = new JogadorDto();
        jogadorA.setId("123");
        jogadorA.setNome("Jogador A");

        JogadorDto jogadorB = new JogadorDto();
        jogadorB.setId("124");
        jogadorB.setNome("Jogador B");


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