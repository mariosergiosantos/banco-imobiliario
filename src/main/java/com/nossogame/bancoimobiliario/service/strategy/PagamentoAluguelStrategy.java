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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PAGAMENTO_ALUGUEL")
public class PagamentoAluguelStrategy implements TransacaoStrategy {

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public Transacao executar(Sala sala, Jogador pagador, Propriedade propriedade, double valor) throws RegraNegocialException, ResourceNotFoundException {
        Jogador recebedor = propriedade.getDono();
        if (recebedor == null) {
            throw new RegraNegocialException("A propriedade não pertence a nenhum jogador.");
        }
        if (pagador.getSaldo() < valor) {
            throw new RegraNegocialException("Saldo insuficiente para pagar o aluguel.");
        }

        jogadorService.debitarSaldo(pagador.getId(), valor);
        jogadorService.creditarSaldo(recebedor.getId(), valor);

        Transacao transacao = TransacaoFactory.criarTransacaoPagamentoAluguel(sala, pagador, recebedor, propriedade, valor, "");
        return transacaoRepository.save(transacao);
    }
}
