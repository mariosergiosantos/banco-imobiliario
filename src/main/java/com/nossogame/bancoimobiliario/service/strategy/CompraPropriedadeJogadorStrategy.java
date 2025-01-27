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
import com.nossogame.bancoimobiliario.service.validation.TransactionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("COMPRA_PROPRIEDADE_JOGADOR")
public class CompraPropriedadeJogadorStrategy implements TransacaoStrategy {

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransactionValidation transactionValidation;

    @Override
    public Transacao executar(Sala sala, Jogador comprador, Propriedade propriedade, double valorTransacao) throws RegraNegocialException, ResourceNotFoundException {
        Jogador vendedor = propriedade.getDono();

        if (Objects.isNull(vendedor)) {
            throw new RegraNegocialException("Propriedade não está comprada");
        }

        if (comprador.getId().equals(vendedor.getId())) {
            throw new RegraNegocialException("Você já é o proprietário da propriedade.");
        }

        transactionValidation.validarPropriedadePertenceAoVendedor(propriedade, vendedor);

        transactionValidation.validarSaldo(comprador, valorTransacao);

        jogadorService.debitarSaldo(comprador, valorTransacao);
        jogadorService.creditarSaldo(vendedor, valorTransacao);

        propriedade.setDono(comprador);

        propriedadeService.atualizarPropriedade(propriedade);

        String descricao = String.format("Compra da propriedade %s por %s, vendedor %s com valor %.2f",
                propriedade.getNome(),
                comprador.getNome(),
                vendedor.getNome(),
                valorTransacao);

        Transacao transacao = TransacaoFactory.criarCompraPropriedadeJogador(sala, comprador, vendedor, propriedade, valorTransacao, descricao);
        return transacaoRepository.save(transacao);
    }
}