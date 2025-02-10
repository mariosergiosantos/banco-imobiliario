package com.nossogame.bancoimobiliario.dto;

import com.nossogame.bancoimobiliario.model.enuns.StatusSala;

import java.time.LocalDateTime;
import java.util.List;

public record SalaDto(String id, LocalDateTime dataCriacao, StatusSala status, String administradorId,
                      List<JogadorDto> jogadores, List<PropriedadeDto> propriedades) {

}
