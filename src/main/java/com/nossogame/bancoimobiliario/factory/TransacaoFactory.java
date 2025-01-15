package com.nossogame.bancoimobiliario.factory;

import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.Transacao;
import com.nossogame.bancoimobiliario.model.enuns.TipoTransacao;

import static com.nossogame.bancoimobiliario.config.AppConstantes.SALARIO_JOGADOR;

public class TransacaoFactory {

    public static Transacao criarCompraPropriedadeBanco(Sala sala, Jogador comprador, Propriedade propriedade, String descricao) {
        return new Transacao(sala, comprador, null, propriedade, TipoTransacao.COMPRA_PROPRIEDADE_DO_BANCO, propriedade.getValorCompra(), descricao);
    }

    public static Transacao criarCompraPropriedadeJogador(Sala sala, Jogador comprador, Jogador vendedor, Propriedade propriedade, double valor, String descricao) {
        return new Transacao(sala, comprador, vendedor, propriedade, TipoTransacao.COMPRA_PROPRIEDADE_JOGADOR, valor, descricao);
    }

    public static Transacao criarTransacaoPagamentoAluguel(Sala sala, Jogador pagador, Jogador recebedor, Propriedade propriedade, String descricao) {
        return new Transacao(sala, pagador, recebedor, propriedade, TipoTransacao.PAGAMENTO_ALUGUEL, propriedade.getValorAluguelAtual(), descricao);
    }

    public static Transacao criarPagamentoSalario(Sala sala, Jogador jogador, double salario, String descricao) {
        return new Transacao(sala, jogador, null, null, TipoTransacao.PAGAMENTO_SALARIO, salario, descricao);
    }

    public static Transacao criarTransacaoContruirPropriedade(Sala sala, Jogador pagador, Propriedade propriedade, double valor, String descricao) {
        return new Transacao(sala, pagador, null, propriedade, TipoTransacao.CONSTRUIR_PROPRIEDADE, valor, descricao);
    }
}
