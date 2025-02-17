package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.config.Metric;
import com.nossogame.bancoimobiliario.dto.TransacaoDto;
import com.nossogame.bancoimobiliario.dto.request.TransacaoRequestDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.mapper.TransacaoMapper;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.Transacao;
import com.nossogame.bancoimobiliario.repository.TransacaoRepository;
import com.nossogame.bancoimobiliario.service.strategy.PagamentoSalarioStrategy;
import com.nossogame.bancoimobiliario.service.strategy.TransacaoStrategy;
import com.nossogame.bancoimobiliario.service.validation.TransactionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransactionValidation transactionValidation;

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private Map<String, TransacaoStrategy> transacaoStrategies;

    @Transactional
    public TransacaoDto registrarTransacao(String salaId, TransacaoRequestDto transacaoRequest) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = transactionValidation.validarSalaEmAndamento(salaId);
        Jogador jogador = jogadorService.buscarJodagor(transacaoRequest.compradorId(), salaId);

        TransacaoStrategy strategy = transacaoStrategies.get(transacaoRequest.tipoTransacao().name());
        if (strategy == null) {
            throw new IllegalArgumentException("Tipo de transação inválido: " + transacaoRequest.tipoTransacao().name());
        }

        Transacao transacao;
        if (strategy instanceof PagamentoSalarioStrategy) {
            transacao = strategy.executar(sala, jogador, null, 0d);
        } else {
            Propriedade propriedade = propriedadeService.buscarPropriedade(transacaoRequest.propriedadeId(), sala.getId());
            transacao = strategy.executar(sala, jogador, propriedade, transacaoRequest.valor());
        }

        return TransacaoMapper.INSTANCE.toDTO(transacao);
    }

    public Transacao save(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    @Metric(name = "listarTransacoesDaSala")
    public List<TransacaoDto> listarTransacoesDaSala(String salaId) {
        return TransacaoMapper.INSTANCE.toDTO(transacaoRepository.findBySalaId(salaId)
                .orElse(new ArrayList<>()));
    }
}
