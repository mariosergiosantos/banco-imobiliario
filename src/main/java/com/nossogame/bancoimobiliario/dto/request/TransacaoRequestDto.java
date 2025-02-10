package com.nossogame.bancoimobiliario.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.nossogame.bancoimobiliario.model.enuns.TipoTransacao;
import jakarta.validation.constraints.NotNull;

public record TransacaoRequestDto(@NotNull TipoTransacao tipoTransacao, String propriedadeId,
                                  @JsonAlias({"compradorId", "jogadorId"}) String compradorId, double valor) {

}
