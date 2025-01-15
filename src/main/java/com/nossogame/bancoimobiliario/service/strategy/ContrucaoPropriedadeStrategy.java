package com.nossogame.bancoimobiliario.service.strategy;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.Transacao;

public class ContrucaoPropriedadeStrategy implements TransacaoStrategy {

    @Override
    public Transacao executar(Sala sala, Jogador jogador, Propriedade propriedade, double valor) throws ResourceNotFoundException, RegraNegocialException {

        return null;
    }
}
