package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.RankingDto;
import com.nossogame.bancoimobiliario.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping
    public ResponseEntity<List<RankingDto>> listarRanking() {
        return ResponseEntity.ok(rankingService.listarRankingGeral());
    }

    @GetMapping("/{jogadorId}")
    public ResponseEntity<List<RankingDto>> listarRankingPorJogador(@PathVariable String jogadorId) {
        return ResponseEntity.ok(rankingService.listarRankingPorJogador(jogadorId));
    }
}


