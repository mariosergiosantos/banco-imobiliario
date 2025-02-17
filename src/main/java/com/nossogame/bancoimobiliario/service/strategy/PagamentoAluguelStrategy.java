package com.nossogame.bancoimobiliario.service.strategy;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.factory.TransacaoFactory;
import com.nossogame.bancoimobiliario.model.*;
import com.nossogame.bancoimobiliario.model.enuns.TipoAluguel;
import com.nossogame.bancoimobiliario.repository.AluguelPropriedadeRepository;
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

    @Autowired
    private AluguelPropriedadeRepository aluguelPropriedadeRepository;

    @Override
    public Transacao executar(Sala sala, Jogador pagador, Propriedade propriedade, double valor) throws RegraNegocialException, ResourceNotFoundException {
        Jogador recebedor = propriedade.getDono();

        if (propriedade instanceof Companhia) {
            throw new RegraNegocialException("Apenas propriedades do tipo Casa permitem pagamento de aluguel.");
        }

        if (Objects.isNull(recebedor)) {
            throw new RegraNegocialException("A propriedade não pertence a nenhum jogador.");
        }

        if (propriedade.isHipotecada()) {
            throw new RegraNegocialException("Não é possível pagar aluguel de propriedade hipotecada.");
        }

        Casa casa = (Casa) propriedade;

        double aluguel = calcularAluguel(casa);

        if (pagador.getSaldo() < aluguel) {
            throw new RegraNegocialException("Saldo insuficiente para pagar o aluguel");
        }

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

    public double calcularAluguel(Casa propriedade) throws ResourceNotFoundException {
        TipoAluguel tipoAluguel;

        if (propriedade.isHotel()) {
            tipoAluguel = TipoAluguel.HOTEL;
        } else {
            switch (propriedade.getNumeroCasas()) {
                case 1 -> tipoAluguel = TipoAluguel.CASA_1;
                case 2 -> tipoAluguel = TipoAluguel.CASA_2;
                case 3 -> tipoAluguel = TipoAluguel.CASA_3;
                case 4 -> tipoAluguel = TipoAluguel.CASA_4;
                default -> tipoAluguel = TipoAluguel.BASE;
            }
        }

        return aluguelPropriedadeRepository.findByPropriedadeAndTipoAluguel(propriedade, tipoAluguel)
                .map(AluguelPropriedade::getValor)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado para essa propriedade"));
    }
}
