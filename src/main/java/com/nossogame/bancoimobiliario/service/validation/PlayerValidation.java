package com.nossogame.bancoimobiliario.service.validation;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.model.Sala;
import org.springframework.stereotype.Component;

import static com.nossogame.bancoimobiliario.config.AppConstantes.QUANTIDADE_MAXIMA_JOGADORES;
import static com.nossogame.bancoimobiliario.model.enuns.StatusSala.ABERTA;

@Component
public class PlayerValidation {

    public void validateCreatePlayer(Sala sala) throws RegraNegocialException {

        if (!sala.getStatus().equals(ABERTA)) {
            throw new RegraNegocialException("Sala não está aberta para entrada de novos jogadores");
        }

        if (!(sala.getJogadores().size() < QUANTIDADE_MAXIMA_JOGADORES)) {
            throw new RegraNegocialException("Sala já preenhida com maximo de jogadores");
        }

    }
}
