package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.request.CartaImpactoRequestDto;
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
        Jogador jogador = jogadorService.buscarJodagor(impacto.salaId(), jogadorId);

        //TODO registrar transação
        switch (impacto.tipo()) {
            case SorteReves.SORTE -> jogadorService.creditarSaldo(jogador, impacto.valor());
            case SorteReves.REVES -> jogadorService.debitarSaldo(jogador, impacto.valor());
            default -> throw new IllegalArgumentException("Tipo inválido");
        }
    }
}
