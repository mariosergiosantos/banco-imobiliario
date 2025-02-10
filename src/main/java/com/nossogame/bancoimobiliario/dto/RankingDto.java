package com.nossogame.bancoimobiliario.dto;

import java.time.LocalDateTime;

public record RankingDto(String nomeJogador, double saldoFinal, int numeroPropriedades, LocalDateTime dataVitoria) {
    
}

