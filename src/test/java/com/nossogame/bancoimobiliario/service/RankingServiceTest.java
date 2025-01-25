package com.nossogame.bancoimobiliario.service;


import br.com.six2six.fixturefactory.Fixture;
import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.RankingDto;
import com.nossogame.bancoimobiliario.model.Casa;
import com.nossogame.bancoimobiliario.model.Ranking;
import com.nossogame.bancoimobiliario.repository.RankingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RankingServiceTest extends AbstractTest {

    @InjectMocks
    private RankingService rankingService;

    @Mock
    private RankingRepository rankingRepository;

    @Test
    void deveRegistrarVitoriaComSucesso() {
        Ranking ranking = Fixture.from(Ranking.class).gimme("valido");
        jogador.setPropriedades(Fixture.from(Casa.class).gimme(6, "casa"));

        when(rankingRepository.save(any())).thenReturn(ranking);

        RankingDto resultado = rankingService.registrarVitoria(jogador, sala);

        assertNotNull(resultado);
        assertEquals(ranking.getJogador().getNome(), resultado.getNomeJogador());
        assertEquals(ranking.getSaldoFinal(), resultado.getSaldoFinal());

        verify(rankingRepository).save(any());
    }

    @Test
    void deveListarRankingOrdenadoPorVitorias() {
        List<Ranking> ranking = Fixture.from(Ranking.class).gimme(5, "valido");

        when(rankingRepository.findTop10ByOrderBySaldoFinalDesc()).thenReturn(ranking);

        List<RankingDto> resultado = rankingService.listarRankingGeral();

        assertEquals(5, resultado.size());

        verify(rankingRepository).findTop10ByOrderBySaldoFinalDesc();
    }
}