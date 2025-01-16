package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.VencedorJogoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.mapper.JogadorMapper;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.service.validation.GameValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private EmprestimoService emprestimoService;

    public void iniciar(String salaId) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = salaService.buscarSalaPorId(salaId);

        gameValidation.startGameValidate(sala);

        propriedadeService.cadastrarPropriedadesBase(sala);

        sala.setStatus(StatusSala.EM_ANDAMENTO);

        salaService.atualizarSala(sala);
    }

    public VencedorJogoDto finalizar(String salaId) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = salaService.buscarSalaPorId(salaId);

        gameValidation.endGameValidation(sala);

        Jogador vencedor = determinarVencedor(sala.getId());

        rankingService.registrarVitoria(vencedor, sala);

        sala.setStatus(StatusSala.ENCERRADA);
        salaService.atualizarSala(sala);

        return JogadorMapper.INSTANCE.toDto(vencedor);
    }

    private Jogador determinarVencedor(String salaId) throws RegraNegocialException, ResourceNotFoundException {
        List<Jogador> jogadores = jogadorService.buscarJogadoresPorSala(salaId);

        List<Jogador> jogadoresElegiveis = jogadores.stream()
                .filter(jogador -> !temEmprestimoPendente(jogador))
                .collect(Collectors.toList());

        if (jogadoresElegiveis.isEmpty()) {
            throw new RegraNegocialException("Nenhum jogador é elegível para vencer a partida.");
        }

        return jogadoresElegiveis.stream()
                .map(jogador -> {
                    jogador.setSaldoPropriedades(propriedadeService.calcularValorTotalPropriedades(jogador));
                    return jogador;
                })
                .max(Comparator.comparingDouble(this::calcularPatrimonioLiquido))
                .orElseThrow(() -> new IllegalStateException("Erro ao determinar o vencedor."));
    }

    private boolean temEmprestimoPendente(Jogador jogador) {
        return emprestimoService.existsByJogadorDestinoAndStatus(jogador, StatusEmprestimo.PENDENTE);
    }

    private double calcularPatrimonioLiquido(Jogador jogador) {
        double saldo = jogador.getSaldo();
        double valorPropriedades = jogador.getSaldoPropriedades();
        return saldo + valorPropriedades;
    }
}
