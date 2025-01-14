package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.SalaDto;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.mapper.SalaMapper;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    public Sala criarSala() {
        Sala sala = new Sala();
        sala.setStatus(StatusSala.ABERTA);
        return salaRepository.save(sala);
    }

    public Sala atualizarSala(Sala sala) {
        return salaRepository.save(sala);
    }

    public Sala buscarSalaPorId(String id) throws ResourceNotFoundException {
        return salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada"));
    }

    public List<SalaDto> listarTodasSalas() {
        return SalaMapper.INSTANCE.toDTOs(salaRepository.findAll());
    }

}

