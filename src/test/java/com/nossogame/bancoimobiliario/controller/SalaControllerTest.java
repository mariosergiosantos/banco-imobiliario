package com.nossogame.bancoimobiliario.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.request.SalaRequestDto;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.mapper.SalaMapper;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.service.SalaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SalaControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaService salaService;

    @Test
    void deveCriarSalaComSucesso() throws Exception {

        Sala sala = Fixture.from(Sala.class).gimme("valida-criada");

        when(salaService.criarSala(any(SalaRequestDto.class))).thenReturn(SalaMapper.INSTANCE.toDTO(sala));

        mockMvc.perform(post("/api/v1/salas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "nomeJogadorAdm": "Mário"
                                    }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("ABERTA"))
                .andExpect(jsonPath("$.dataCriacao").exists())
                .andExpect(jsonPath("$.jogadores").isNotEmpty())
                .andExpect(jsonPath("$.propriedades").isEmpty())
                .andExpect(header().exists("X-Correlation-Id"));

        verify(salaService).criarSala(any(SalaRequestDto.class));
    }

    @Test
    void deveRetornarErroQuandoNaoEncontrarSala() throws Exception {

        when(salaService.buscarSala(codigoSala))
                .thenThrow(new ResourceNotFoundException("Sala não encontrada"));

        mockMvc.perform(get("/api/v1/salas/" + codigoSala)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "nomeJogadorAdm": "JogadorA"
                                    }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Sala não encontrada"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(header().exists("X-Correlation-Id"));

        verify(salaService).buscarSala(codigoSala);
    }

    @Test
    void deveRetornarDadosDaSala() throws Exception {
        Sala sala = Fixture.from(Sala.class).gimme("valida-criada", new Rule() {{
            add("id", codigoSala);
        }});

        when(salaService.buscarSala(codigoSala)).thenReturn(SalaMapper.INSTANCE.toDTO(sala));

        mockMvc.perform(get("/api/v1/salas/" + codigoSala)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(codigoSala))
                .andExpect(jsonPath("$.status").value("ABERTA"))
                .andExpect(jsonPath("$.dataCriacao").exists())
                .andExpect(jsonPath("$.jogadores").isNotEmpty())
                .andExpect(jsonPath("$.propriedades").isEmpty())
                .andExpect(header().exists("X-Correlation-Id"));

        verify(salaService).buscarSala(codigoSala);
    }

    @Test
    void deveRetornarSalasCriadas() throws Exception {
        mockMvc.perform(get("/api/v1/salas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(header().exists("X-Correlation-Id"));
    }
}

