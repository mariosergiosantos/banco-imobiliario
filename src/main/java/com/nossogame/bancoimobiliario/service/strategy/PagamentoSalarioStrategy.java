package com.nossogame.bancoimobiliario.service.strategy;

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

import static com.nossogame.bancoimobiliario.config.AppConstantes.SALARIO_JOGADOR;

@Component("PAGAMENTO_SALARIO")
public class PagamentoSalarioStrategy implements TransacaoStrategy {

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public Transacao executar(Sala sala, Jogador jogador, Propriedade propriedade, double valor) throws ResourceNotFoundException {
        jogadorService.creditarSaldo(jogador, SALARIO_JOGADOR);

        String descricao = String.format("Pagamento de salário para %s com valor %.2f",
                jogador.getNome(),
                SALARIO_JOGADOR);

        Transacao transacao = TransacaoFactory.criarPagamentoSalario(sala, jogador, SALARIO_JOGADOR, descricao);

        return transacaoRepository.save(transacao);
    }
}
