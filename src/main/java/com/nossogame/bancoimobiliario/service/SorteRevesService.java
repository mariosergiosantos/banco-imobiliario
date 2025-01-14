package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.CartaImpactoRequestDto;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Jogador;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SorteRevesService {

    @Autowired
    private JogadorService jogadorService;

    @Transactional
    public void aplicarImpacto(String idSala,  CartaImpactoRequestDto impacto) throws ResourceNotFoundException {
        Jogador jogador = jogadorService.buscarJodagor(idSala, impacto.getJogadorId());
        jogadorService.atualizarSaldo(jogador, jogador.getSaldo() + impacto.getValor());
    }
}
