package com.nossogame.bancoimobiliario.factory;

import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.Transacao;
import com.nossogame.bancoimobiliario.model.enuns.TipoTransacao;

public class TransacaoFactory {

    public static Transacao criarCompraPropriedadeBanco(Sala sala, Jogador comprador, Propriedade propriedade, String descricao) {
        return new Transacao(sala, comprador, propriedade, TipoTransacao.COMPRA_PROPRIEDADE_DO_BANCO, propriedade.getValorCompra(), descricao);
    }

    public static Transacao criarCompraPropriedadeJogador(Sala sala, Jogador comprador, Jogador vendedor, Propriedade propriedade, String descricao) {
        return new Transacao(sala, comprador, vendedor, propriedade, TipoTransacao.TRANSFERENCIA_PROPRIEDADE, propriedade.getValorCompra(), descricao);
    }

    public static Transacao criarTransacaoPagamentoAluguel(Sala sala, Jogador comprador, Jogador vendedor, Propriedade propriedade, double valor, String descricao) {
        return new Transacao(sala, comprador, vendedor, propriedade, TipoTransacao.PAGAMENTO_ALUGUEL, valor, descricao);
    }

    public static Transacao criarPagamentoSalario(Sala sala, Jogador jogador, double salarioJogador, String descricao) {
        return new Transacao(sala, jogador, TipoTransacao.PAGAMENTO_SALARIO, salarioJogador, descricao);
    }
}
