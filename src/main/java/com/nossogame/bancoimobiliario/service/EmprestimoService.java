package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.EmprestimoDto;
import com.nossogame.bancoimobiliario.dto.SolicitarEmprestimoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.factory.TransacaoFactory;
import com.nossogame.bancoimobiliario.mapper.EmprestimoMapper;
import com.nossogame.bancoimobiliario.model.Emprestimo;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.Transacao;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.repository.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmprestimoService {

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private SalaService salaService;

    @Transactional
    public EmprestimoDto solicitarEmprestimo(SolicitarEmprestimoDto solicitacaoEmprestimo) throws RegraNegocialException, ResourceNotFoundException {

        Sala sala = salaService.buscarSalaPorId(solicitacaoEmprestimo.salaId());
        if (!sala.getStatus().equals(StatusSala.EM_ANDAMENTO)) {
            throw new RegraNegocialException("Sala não está disponível para realizar transações.");
        }

        Jogador pagador = jogadorService.findById(solicitacaoEmprestimo.pagadorId());

        if (pagador.getSaldo() < solicitacaoEmprestimo.valorContratado()) {
            throw new RegraNegocialException("O jogador não possui saldo suficiente para conceder o empréstimo.");
        }

        Jogador recebedor = jogadorService.findById(solicitacaoEmprestimo.recebedorId());

        if (solicitacaoEmprestimo.valorAcordado() < solicitacaoEmprestimo.valorContratado()) {
            throw new RegraNegocialException("O valor acordado não pode ser menor que o valor contratado.");
        }

        jogadorService.debitarSaldo(pagador, solicitacaoEmprestimo.valorContratado());
        jogadorService.creditarSaldo(recebedor, solicitacaoEmprestimo.valorAcordado());

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setRecebedor(recebedor);
        emprestimo.setPagador(pagador);
        emprestimo.setSala(sala);
        emprestimo.setValorContratado(solicitacaoEmprestimo.valorContratado());
        emprestimo.setValorDevolucao(solicitacaoEmprestimo.valorAcordado());
        emprestimo.setSaldoDevedor(solicitacaoEmprestimo.valorAcordado());
        emprestimo.setStatus(StatusEmprestimo.PENDENTE);
        emprestimo.setDataEmprestimo(LocalDateTime.now());

        String descricao = String.format(
                "Empréstimo de %.2f solicitado por %s para %s",
                solicitacaoEmprestimo.valorContratado(),
                pagador.getNome(),
                recebedor.getNome()
        );

        Transacao transacao = TransacaoFactory.criarTransacaoEmprestimo(sala, pagador, recebedor, solicitacaoEmprestimo.valorAcordado(), descricao);

        transacaoService.save(transacao);

        return EmprestimoMapper.INSTANCE.toDTO(emprestimoRepository.save(emprestimo));
    }


    @Transactional
    public EmprestimoDto pagarEmprestimo(String emprestimoId, double valor) throws ResourceNotFoundException, RegraNegocialException {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado."));

        if (emprestimo.getStatus().equals(StatusEmprestimo.ENCERRADO)) {
            throw new RegraNegocialException("O empréstimo já foi encerrado.");
        }

        Jogador pagador = emprestimo.getPagador();
        Jogador recebedor = emprestimo.getRecebedor();

        if (pagador.getSaldo() < valor) {
            throw new RegraNegocialException("Saldo insuficiente para realizar o pagamento.");
        }

        if (valor > emprestimo.getSaldoDevedor()) {
            throw new RegraNegocialException("O valor do pagamento excede o saldo devedor.");
        }

        jogadorService.debitarSaldo(pagador, valor);
        jogadorService.creditarSaldo(recebedor, valor);

        emprestimo.setSaldoDevedor(emprestimo.getSaldoDevedor() - valor);

        if (emprestimo.getSaldoDevedor() == 0) {
            emprestimo.setStatus(StatusEmprestimo.ENCERRADO);
        }

        String descricao = String.format(
                "Pagamento de %.2f realizado por %s para %s",
                valor,
                pagador.getNome(),
                recebedor.getNome()
        );

        Transacao transacao = TransacaoFactory.criarTransacaoPagarEmprestimo(emprestimo.getSala(), pagador, recebedor, valor, descricao);

        transacaoService.save(transacao);

        return EmprestimoMapper.INSTANCE.toDTO(emprestimoRepository.save(emprestimo));
    }


    public boolean existsByRecebedorAndStatus(Jogador jogador, StatusEmprestimo statusEmprestimo) {
        return emprestimoRepository.existsByRecebedorAndStatus(jogador, statusEmprestimo);
    }

    public EmprestimoDto buscarEmprestimoPorId(String id) throws ResourceNotFoundException {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado."));

        return EmprestimoMapper.INSTANCE.toDTO(emprestimo);
    }
}

