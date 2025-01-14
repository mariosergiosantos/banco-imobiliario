package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.JogadorDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.mapper.JogadorMapper;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.repository.JogadorRepository;
import com.nossogame.bancoimobiliario.service.validation.PlayerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nossogame.bancoimobiliario.config.AppConstantes.SALDO_INICIAL_JOGADOR;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private SalaService salaService;

    @Autowired
    private PlayerValidation playerValidation;

    public JogadorDto adicionarJogador(String salaId, Jogador jogador)
            throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = salaService.buscarSalaPorId(salaId);

        playerValidation.validateCreatePlayer(sala);

        jogador.setSala(sala);
        jogador.setSaldo(SALDO_INICIAL_JOGADOR);

        try {
            jogadorRepository.save(jogador);
        } catch (DataIntegrityViolationException e) {
            throw new RegraNegocialException("Nome já cadastrado para outro jogador");
        }

        return JogadorMapper.INSTANCE.toDTO(jogador);
    }

    public Jogador buscarJodagor(String jogadorId, String salaId) throws ResourceNotFoundException {
        Jogador jogador = jogadorRepository.findByIdAndSalaId(jogadorId, salaId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrada"));

        return jogador;
    }

    public List<Jogador> listarJogadoresDaSala(String salaId) throws ResourceNotFoundException {
        Sala sala = salaService.buscarSalaPorId(salaId);
        return sala.getJogadores();
    }

    public Jogador atualizarSaldo(Jogador jogador, double novoSaldo) {
        jogador.setSaldo(novoSaldo);
        return jogadorRepository.save(jogador);
    }

    public Jogador debitarSaldo(String jogadorId, double saldo) throws ResourceNotFoundException {
        Jogador jogador = jogadorRepository.findById(jogadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado"));
        jogador.setSaldo(jogador.getSaldo() - saldo);
        return jogadorRepository.save(jogador);
    }

    public Jogador creditarSaldo(String jogadorId, double saldo) throws ResourceNotFoundException {
        Jogador jogador = jogadorRepository.findById(jogadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado"));
        jogador.setSaldo(jogador.getSaldo() + saldo);
        return jogadorRepository.save(jogador);
    }
}
