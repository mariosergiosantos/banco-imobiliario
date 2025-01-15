package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.ComprarPropriedadeBancoRequestDto;
import com.nossogame.bancoimobiliario.dto.ComprarPropriedadeJogadorRequestDto;
import com.nossogame.bancoimobiliario.dto.PagamentoAluguelRequestDto;
import com.nossogame.bancoimobiliario.dto.TransacaoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.factory.TransacaoFactory;
import com.nossogame.bancoimobiliario.mapper.TransacaoMapper;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.Transacao;
import com.nossogame.bancoimobiliario.repository.TransacaoRepository;
import com.nossogame.bancoimobiliario.service.strategy.TransacaoStrategy;
import com.nossogame.bancoimobiliario.service.validation.TransactionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nossogame.bancoimobiliario.config.AppConstantes.SALARIO_JOGADOR;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransactionValidation transactionValidation;

    @Autowired
    private SalaService salaService;

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private Map<String, TransacaoStrategy> transacaoStrategies;

    @Transactional
    public TransacaoDto processarTransacao(String tipo, Sala sala, Jogador jogador, Propriedade propriedade, double valor) throws ResourceNotFoundException, RegraNegocialException {
        TransacaoStrategy strategy = transacaoStrategies.get(tipo);
        if (strategy == null) {
            throw new IllegalArgumentException("Tipo de transação inválido: " + tipo);
        }

        Transacao transacao = strategy.executar(sala, jogador, propriedade, valor);
        return TransacaoMapper.INSTANCE.toDTO(transacao);
    }

    @Transactional
    public TransacaoDto comprarPropriedadeBanco(String salaId, ComprarPropriedadeBancoRequestDto transacaoRequest) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = transactionValidation.validarSalaEmAndamento(salaId);
        Propriedade propriedade = propriedadeService.buscarPropriedade(transacaoRequest.getIdPropriedade(), sala.getId());
        Jogador comprador = jogadorService.buscarJodagor(transacaoRequest.getJogadorId(), salaId);

        transactionValidation.validarPropriedadeDisponivel(propriedade);
        transactionValidation.validarSaldo(comprador, propriedade.getValorCompra());

        jogadorService.debitarSaldo(comprador.getId(), propriedade.getValorCompra());

        propriedade.setDono(comprador);
        propriedadeService.atualizarPropriedade(propriedade);

        String descricao = "Compra da propriedade: " + propriedade.getNome() + " por " + comprador.getNome();

        Transacao transacao = TransacaoFactory.criarCompraPropriedadeBanco(sala, comprador, propriedade, descricao);

        return TransacaoMapper.INSTANCE.toDTO(transacao);
    }

    @Transactional
    public TransacaoDto comprarPropriedadeJogador(String salaId, ComprarPropriedadeJogadorRequestDto transacaoRequest) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = transactionValidation.validarSalaEmAndamento(salaId);
        Propriedade propriedade = propriedadeService.buscarPropriedade(transacaoRequest.getIdPropriedade(), sala.getId());
        Jogador comprador = jogadorService.buscarJodagor(transacaoRequest.getCompradorId(), salaId);
        Jogador vendedor = jogadorService.buscarJodagor(transacaoRequest.getVendedorId(), salaId);

        transactionValidation.validarPropriedadePertenceAoVendedor(propriedade, vendedor);
        transactionValidation.validarSaldo(comprador, transacaoRequest.getValor());

        propriedade.setDono(comprador);

        jogadorService.debitarSaldo(comprador.getId(), transacaoRequest.getValor());
        jogadorService.creditarSaldo(vendedor.getId(), transacaoRequest.getValor());

        propriedadeService.atualizarPropriedade(propriedade);

        String descricao = "Compra da propriedade: " + propriedade.getNome() + " por " + comprador.getNome() + ", vendedor: " + vendedor.getNome();

        Transacao transacao = TransacaoFactory.criarCompraPropriedadeJogador(sala, comprador, vendedor, propriedade, transacaoRequest.getValor(), descricao);

        return TransacaoMapper.INSTANCE.toDTO(transacao);
    }

    @Transactional
    public TransacaoDto pagamentoSalario(String salaId, String jogadorId) throws ResourceNotFoundException, RegraNegocialException {
        Jogador jogador = jogadorService.buscarJodagor(jogadorId, salaId);

        Sala sala = transactionValidation.validarSalaEmAndamento(jogador.getSala().getId());

        jogadorService.creditarSaldo(jogador.getId(), SALARIO_JOGADOR);

        String descricao = "Pagamento de salário para " + jogador.getNome();

        Transacao transacao = TransacaoFactory.criarPagamentoSalario(sala, jogador, SALARIO_JOGADOR, descricao);

        return TransacaoMapper.INSTANCE.toDTO(transacao);
    }

    public List<TransacaoDto> listarTransacoesDaSala(String salaId) {
        return TransacaoMapper.INSTANCE.toDTO(transacaoRepository.findBySalaId(salaId)
                .orElse(new ArrayList<>()));
    }

    @Transactional
    public TransacaoDto pagamentoAluguel(String salaId, PagamentoAluguelRequestDto requestDto)
            throws ResourceNotFoundException, RegraNegocialException {

        Sala sala = transactionValidation.validarSalaEmAndamento(salaId);

        Propriedade propriedade = propriedadeService.buscarPropriedade(requestDto.getPropriedadeId(), salaId);

        Jogador jogadorProprietario = propriedade.getDono();
        if (jogadorProprietario == null) {
            throw new RegraNegocialException("A propriedade não pertence a nenhum jogador.");
        }

        if (jogadorProprietario.getId().equals(requestDto.getJogadorPaganteId())) {
            throw new RegraNegocialException("Você não pode pagar aluguel para uma propriedade que já é sua.");
        }

        Jogador jogadorPagante = jogadorService.buscarJodagor(requestDto.getJogadorPaganteId(), salaId);

        double valorAluguel = propriedade.getValorAluguelAtual();
        if (jogadorPagante.getSaldo() < valorAluguel) {
            throw new RegraNegocialException("Saldo insuficiente para pagar o aluguel.");
        }

        jogadorService.debitarSaldo(jogadorPagante.getId(), valorAluguel);
        jogadorService.creditarSaldo(jogadorProprietario.getId(), valorAluguel);

        Transacao transacao = TransacaoFactory.criarTransacaoPagamentoAluguel(sala, jogadorPagante, propriedade.getDono(), propriedade, "Pagamento de aluguel da propriedade: " + propriedade.getNome());

        return TransacaoMapper.INSTANCE.toDTO(transacao);
    }

    public Transacao save(Transacao transacao)  {
        return transacaoRepository.save(transacao);
    }
}
