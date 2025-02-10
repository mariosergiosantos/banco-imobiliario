package com.nossogame.bancoimobiliario.dto.request;

import com.nossogame.bancoimobiliario.model.enuns.SorteReves;

public record CartaImpactoRequestDto(String salaId, int valor, SorteReves tipo) {
}
