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

@Component("COMPRA_PROPRIEDADE_DO_BANCO")
public class CompraPropriedadeBancoStrategy implements TransacaoStrategy {

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransactionValidation transactionValidation;

    @Override
    public Transacao executar(Sala sala, Jogador comprador, Propriedade propriedade, double valor)
            throws RegraNegocialException {

        transactionValidation.validarPropriedadeDisponivel(propriedade);
        transactionValidation.validarSaldo(comprador, propriedade.getValorCompra());

        jogadorService.debitarSaldo(comprador, propriedade.getValorCompra());

        propriedade.setDono(comprador);

        propriedadeService.atualizarPropriedade(propriedade);

        String descricao = String.format(
                "Compra da propriedade %s efetuada por %s com valor %.2f",
                propriedade.getNome(),
                comprador.getNome(),
                propriedade.getValorCompra()
        );

        Transacao transacao = TransacaoFactory.criarCompraPropriedadeBanco(sala, comprador, propriedade, descricao);
        return transacaoRepository.save(transacao);
    }
}
