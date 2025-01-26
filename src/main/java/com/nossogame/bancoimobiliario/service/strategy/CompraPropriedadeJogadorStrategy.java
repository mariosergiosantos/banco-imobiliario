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

@Component("TRANSFERENCIA_PROPRIEDADE")
public class CompraPropriedadeJogadorStrategy implements TransacaoStrategy {

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public Transacao executar(Sala sala, Jogador comprador, Propriedade propriedade, double valor) throws RegraNegocialException, ResourceNotFoundException {
        Jogador vendedor = propriedade.getDono();
        if (vendedor == null) {
            throw new RegraNegocialException("Propriedade não pertence a nenhum jogador.");
        }
        if (comprador.getId().equals(vendedor.getId())) {
            throw new RegraNegocialException("Você já é o proprietário da propriedade.");
        }
        if (comprador.getSaldo() < propriedade.getValorCompra()) {
            throw new RegraNegocialException("Saldo insuficiente para comprar a propriedade.");
        }

        jogadorService.debitarSaldo(comprador, propriedade.getValorCompra());
        jogadorService.creditarSaldo(vendedor, propriedade.getValorCompra());
        propriedade.setDono(comprador);
        propriedadeService.atualizarPropriedade(propriedade);

        Transacao transacao = TransacaoFactory.criarCompraPropriedadeJogador(sala, comprador, vendedor, propriedade, valor, "");
        return transacaoRepository.save(transacao);
    }
}