package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.CartaImpactoRequestDto;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.enuns.SorteReves;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SorteRevesService {

    @Autowired
    private JogadorService jogadorService;

    @Transactional
    public void aplicarImpacto(String jogadorId, CartaImpactoRequestDto impacto) throws ResourceNotFoundException {
        Jogador jogador = jogadorService.buscarJodagor(impacto.getSalaId(), jogadorId);

        if (impacto.getTipo().equals(SorteReves.SORTE)) {
            jogadorService.creditarSaldo(jogador, impacto.getValor());
        } else {
            jogadorService.creditarSaldo(jogador, impacto.getValor());
        }
    }
}
