package com.nossogame.bancoimobiliario.service.strategy;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.factory.TransacaoFactory;
import com.nossogame.bancoimobiliario.model.*;
import com.nossogame.bancoimobiliario.repository.TransacaoRepository;
import com.nossogame.bancoimobiliario.service.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("PAGAMENTO_ALUGUEL")
public class PagamentoAluguelStrategy implements TransacaoStrategy {

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public Transacao executar(Sala sala, Jogador pagador, Propriedade propriedade, double valor) throws RegraNegocialException, ResourceNotFoundException {
        Jogador recebedor = propriedade.getDono();

        double aluguel = getAluguel(pagador, propriedade, recebedor);

        jogadorService.debitarSaldo(pagador, aluguel);
        jogadorService.creditarSaldo(recebedor, aluguel);

        String descricao = String.format(
                "Pagamento de aluguel da propriedade %s efetuada por %s com valor %.2f",
                propriedade.getNome(),
                pagador.getNome(),
                aluguel
        );

        Transacao transacao = TransacaoFactory.criarTransacaoPagamentoAluguel(sala, pagador, recebedor, propriedade, aluguel, descricao);
        return transacaoRepository.save(transacao);
    }

    private static double getAluguel(Jogador pagador, Propriedade propriedade, Jogador recebedor) throws RegraNegocialException {
        /*double aluguel = (Objects.isNull(propriedade.getValorAluguelAtual()) || propriedade.getValorAluguelAtual() == 0)
                ? propriedade.getAluguelBase()
                : propriedade.getValorAluguelAtual();

        if (propriedade instanceof Casa) {
            Casa casa = (Casa) propriedade;
            if (casa.getNumeroCasas() > 0) {
                aluguel = aluguel * casa.getNumeroCasas();
            }
        }*/

        double aluguel = propriedade.getAluguelBase();

        if (Objects.isNull(recebedor)) {
            throw new RegraNegocialException("A propriedade não pertence a nenhum jogador.");
        }

        if (recebedor.getId().equals(pagador.getId())) {
            throw new RegraNegocialException("Você não pode pagar aluguel para uma propriedade que já é sua.");
        }

        if (pagador.getSaldo() < aluguel) {
            throw new RegraNegocialException("Saldo insuficiente para pagar o aluguel.");
        }

        if (propriedade.isHipotecada()) {
            throw new RegraNegocialException("Não é possível pagar aluguel de propriedade hipotecada.");
        }

        return aluguel;
    }
}
