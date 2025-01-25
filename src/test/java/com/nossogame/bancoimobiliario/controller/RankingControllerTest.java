package com.nossogame.bancoimobiliario.controller;

import br.com.six2six.fixturefactory.Fixture;
import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.mapper.RankingMapper;
import com.nossogame.bancoimobiliario.model.Ranking;
import com.nossogame.bancoimobiliario.service.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RankingControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RankingService rankingService;

    @Test
    void deveListarRankingComSucesso() throws Exception {
        List<Ranking> rankings = Fixture.from(Ranking.class).gimme(3, "valido");

        when(rankingService.listarRankingGeral()).thenReturn(RankingMapper.INSTANCE.toDtoList(rankings));

        mockMvc.perform(get("/api/v1/ranking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeJogador").value("Mário"))
                .andExpect(jsonPath("$[1].nomeJogador").value("Mário"));
    }
}