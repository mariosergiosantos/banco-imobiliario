package com.nossogame.bancoimobiliario.service.strategy;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.factory.TransacaoFactory;
import com.nossogame.bancoimobiliario.model.*;
import com.nossogame.bancoimobiliario.repository.PropriedadeRepository;
import com.nossogame.bancoimobiliario.repository.TransacaoRepository;
import com.nossogame.bancoimobiliario.service.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("CONSTRUIR_PROPRIEDADE")
public class ContrucaoPropriedadeStrategy implements TransacaoStrategy {

    @Autowired
    private PropriedadeRepository propriedadeRepository; //TODO ajustar para service

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public Transacao executar(Sala sala, Jogador jogador, Propriedade propriedade, double valor) throws RegraNegocialException {

        if (!(propriedade instanceof Casa)) {
            throw new RegraNegocialException("Apenas propriedades do tipo Casa permitem construção.");
        }

        Casa casa = (Casa) propriedade;

        double custoConstrucao = calcularCustoConstrucao(casa);
        if (jogador.getSaldo() < custoConstrucao) {
            throw new RegraNegocialException("Saldo insuficiente para construir.");
        }

        if (casa.isHipotecada()) {
            throw new RegraNegocialException("Não é possível construir em propriedades hipotecadas.");
        }

        if (casa.isHotel()) {
            throw new RegraNegocialException("A propriedade já atingiu o limite de construções.");
        }

        if (!verificarPropriedadesDaMesmaCor(casa, jogador)) {
            throw new RegraNegocialException("Para construir é necessário possuir todas as propriedades da mesma cor e com o mesmo número de casas.");
        }

        casa.setNumeroCasas(casa.getNumeroCasas() + 1);

        if (casa.getNumeroCasas() == 4) {
            casa.setHotel(true);
        }

        jogadorService.debitarSaldo(jogador, custoConstrucao);

        propriedadeRepository.save(casa);

        String descricao = String.format(
                "Construção de %s na propriedade %s efetuada por %s com valor %.2f",
                casa.isHotel() ? "hotel" : "casa",
                casa.getNome(),
                jogador.getNome(),
                custoConstrucao
        );

        Transacao transacao = TransacaoFactory.criarTransacaoContruirPropriedade(propriedade.getSala(), jogador, propriedade, custoConstrucao, descricao);

        return transacaoRepository.save(transacao);
    }

    private boolean verificarPropriedadesDaMesmaCor(Casa casa, Jogador jogador) {
        List<Casa> propriedades = propriedadeRepository.findBySalaIdAndCor(casa.getSala().getId(), casa.getCor());

        boolean todasPertencemAoJogador = propriedades.stream()
                .allMatch(propriedade ->
                        propriedade.getDono() != null && jogador.getId().equals(propriedade.getDono().getId()));

        if (!todasPertencemAoJogador) {
            return false;
        }

        int casasMinimas = propriedades.stream()
                .mapToInt(Casa::getNumeroCasas)
                .min()
                .orElse(0);

        return casa.getNumeroCasas() == casasMinimas;
    }

    private double calcularCustoConstrucao(Casa casa) {
        return casa.getValorCompra() * (casa.getNumeroCasas() + 1);
    }

}
