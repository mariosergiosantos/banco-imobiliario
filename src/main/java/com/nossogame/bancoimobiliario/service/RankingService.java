package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.RankingDto;
import com.nossogame.bancoimobiliario.mapper.RankingMapper;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Ranking;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.repository.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankingRepository;

    public List<RankingDto> listarRankingGeral() {
        List<Ranking> rankings = rankingRepository.findTop10ByOrderBySaldoFinalDesc();
        return RankingMapper.INSTANCE.toDtoList(rankings);
    }

    public RankingDto registrarVitoria(Jogador vencedor, Sala sala) {
        int numeroPropriedades = vencedor.getPropriedades().size();

        Ranking ranking = new Ranking();
        ranking.setJogador(vencedor);
        ranking.setSaldoFinal(vencedor.getSaldo());
        ranking.setNumeroPropriedades(numeroPropriedades);
        ranking.setDataVitoria(LocalDateTime.now());
        ranking.setSala(sala);

        return RankingMapper.INSTANCE.toDto(rankingRepository.save(ranking));
    }
}
