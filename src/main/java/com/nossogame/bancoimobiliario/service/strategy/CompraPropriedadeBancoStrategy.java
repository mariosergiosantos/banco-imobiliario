package com.nossogame.bancoimobiliario.service.strategy;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.factory.TransacaoFactory;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.Transacao;
import com.nossogame.bancoimobiliario.repository.TransacaoRepository;
import com.nossogame.bancoimobiliario.service.JogadorService;
import com.nossogame.bancoimobiliario.service.PropriedadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("COMPRA_PROPRIEDADE_DO_BANCO")
public class CompraPropriedadeBancoStrategy implements TransacaoStrategy {

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public Transacao executar(Sala sala, Jogador jogador, Propriedade propriedade, double valor) throws RegraNegocialException, ResourceNotFoundException {
        if (propriedade.getDono() != null) {
            throw new RegraNegocialException("Propriedade já pertence a um jogador.");
        }
        if (jogador.getSaldo() < propriedade.getValorCompra()) {
            throw new RegraNegocialException("Saldo insuficiente para comprar a propriedade.");
        }

        jogadorService.debitarSaldo(jogador.getId(), propriedade.getValorCompra());
        propriedade.setDono(jogador);
        propriedadeService.atualizarPropriedade(propriedade);

        Transacao transacao = TransacaoFactory.criarCompraPropriedadeBanco(sala, jogador, propriedade, "");
        return transacaoRepository.save(transacao);
    }
}
