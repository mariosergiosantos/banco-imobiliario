package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.service.validation.GameValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class JogoService {

    @Autowired
    private GameValidation gameValidation;

    @Autowired
    private SalaService salaService;

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private RankingService rankingService;

    public void iniciar(String id) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = salaService.buscarSalaPorId(id);

        gameValidation.startGameValidate(sala);

        propriedadeService.cadastrarPropriedadesBase(sala);

        sala.setStatus(StatusSala.EM_ANDAMENTO);

        salaService.atualizarSala(sala);
    }

    public void finalizar(String id) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = salaService.buscarSalaPorId(id);

        gameValidation.endGameValidation(sala);

        Jogador vencedor = determinarVencedor(sala.getJogadores());

        rankingService.registrarVencedorNoRanking(vencedor, sala);

        sala.setStatus(StatusSala.ENCERRADA);
        salaService.atualizarSala(sala);
    }

    private Jogador determinarVencedor(List<Jogador> jogadores) {
        return jogadores.stream()
                .max(Comparator.comparingDouble(Jogador::getSaldo))
                .orElseThrow(() -> new IllegalStateException("Nenhum jogador encontrado."));
    }
}
