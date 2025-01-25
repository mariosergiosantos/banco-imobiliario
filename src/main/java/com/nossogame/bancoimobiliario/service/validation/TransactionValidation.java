package com.nossogame.bancoimobiliario.service.validation;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionValidation {

    @Autowired
    private SalaService salaService;

    public void validarSaldo(Jogador jogador, double valor) throws RegraNegocialException {
        if (jogador.getSaldo() < valor) {
            throw new RegraNegocialException(
                    String.format("Saldo insuficiente. Saldo atual: %.2f, valor necessário: %.2f", jogador.getSaldo(), valor)
            );
        }
    }

    public void validarPropriedadeDisponivel(Propriedade propriedade) throws RegraNegocialException {
        if (propriedade.getDono() != null) {
            throw new RegraNegocialException("Propriedade já pertence a um jogador.");
        }
    }

    public void validarPropriedadePertenceAoVendedor(Propriedade propriedade, Jogador vendedor) throws RegraNegocialException {
        if (!vendedor.getId().equals(propriedade.getDono().getId())) {
            throw new RegraNegocialException("Propriedade não pertence ao vendedor.");
        }
    }

    public Sala validarSalaEmAndamento(String salaId) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = salaService.buscarSalaPorId(salaId);
        if (!sala.getStatus().equals(StatusSala.EM_ANDAMENTO)) {
            throw new RegraNegocialException("Sala não está disponível para realizar transações.");
        }
        return sala;
    }
}
