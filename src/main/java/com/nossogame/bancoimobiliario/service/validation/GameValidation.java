package com.nossogame.bancoimobiliario.service.validation;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.model.Sala;
import org.springframework.stereotype.Service;

import static com.nossogame.bancoimobiliario.model.enuns.StatusSala.ABERTA;
import static com.nossogame.bancoimobiliario.model.enuns.StatusSala.EM_ANDAMENTO;

@Service
public class GameValidation {

    public void startGameValidate(Sala sala) throws RegraNegocialException {

        if (!sala.getStatus().equals(ABERTA)) {
            throw new RegraNegocialException("Status da sala não permite inicio do jogo");
        }

        if (sala.getJogadores().size() < 2) {
            throw new RegraNegocialException("Necessário ter ao menos 2 jogadores");
        }
    }

    public void endGameValidation(Sala sala) throws RegraNegocialException {

        if (!sala.getStatus().equals(EM_ANDAMENTO)) {
            throw new RegraNegocialException("Status da sala não permite finalizar jogo");
        }

    }
}
