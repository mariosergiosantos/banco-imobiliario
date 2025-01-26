package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.SalaDto;
import com.nossogame.bancoimobiliario.dto.request.SalaRequestDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.mapper.SalaMapper;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nossogame.bancoimobiliario.config.AppConstantes.SALDO_INICIAL_JOGADOR;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Transactional
    public SalaDto criarSala(SalaRequestDto salaRequestDto) {
        Sala sala = new Sala();
        sala.setStatus(StatusSala.ABERTA);

        Jogador jogador = new Jogador();
        jogador.setSala(sala);
        jogador.setSaldo(SALDO_INICIAL_JOGADOR);
        jogador.setNome(salaRequestDto.getNomeJogadorAdm());
        jogador.setAdmin(true);

        sala.addJogador(jogador);
        sala.setAdministrador(jogador);

        return SalaMapper.INSTANCE.toDTO(salaRepository.save(sala));
    }

    public Sala atualizarSala(Sala sala) {
        return salaRepository.save(sala);
    }

    public Sala buscarSalaPorId(String id) throws ResourceNotFoundException {
        return salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada"));
    }

    public SalaDto buscarSala(String id) throws ResourceNotFoundException {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada"));
        return SalaMapper.INSTANCE.toDTO(sala);
    }

    public List<SalaDto> listarTodasSalas() {
        return SalaMapper.INSTANCE.toDTOs(salaRepository.findAll());
    }

}

