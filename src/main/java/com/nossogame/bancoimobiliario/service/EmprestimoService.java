package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Emprestimo;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
import com.nossogame.bancoimobiliario.repository.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoService {

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Transactional
    public Emprestimo solicitarEmprestimo(String jogadorOrigemId, String jogadorDestinoId, double valorContratado, double valorAcordado) throws RegraNegocialException, ResourceNotFoundException {
        Jogador jogadorOrigem = jogadorService.findById(jogadorOrigemId);
        Jogador jogadorDestino = jogadorService.findById(jogadorDestinoId);

        if (jogadorOrigem.getSaldo() < valorContratado) {
            throw new RegraNegocialException("O jogador origem não possui saldo suficiente para conceder o empréstimo.");
        }

        if (valorAcordado < valorContratado) {
            throw new RegraNegocialException("O valor acordado não pode ser menor que o valor contratado.");
        }

        // Atualiza os saldos
        jogadorService.debitarSaldo(jogadorOrigem.getId(), valorContratado);
        jogadorService.creditarSaldo(jogadorDestino.getId(), valorContratado);

        // Cria e salva o empréstimo
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setJogadorOrigem(jogadorOrigem);
        emprestimo.setJogadorDestino(jogadorDestino);
        emprestimo.setValorContratado(valorContratado);
        emprestimo.setValorAcordado(valorAcordado);
        emprestimo.setSaldoDevedor(valorAcordado);
        emprestimo.setStatus(StatusEmprestimo.PENDENTE);

        return emprestimoRepository.save(emprestimo);
    }


    @Transactional
    public void pagarEmprestimo(String emprestimoId, String jogadorDestinoId, double valor) throws ResourceNotFoundException, RegraNegocialException {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado."));

        if (!emprestimo.getJogadorDestino().getId().equals(jogadorDestinoId)) {
            throw new RegraNegocialException("O jogador informado não é o devedor deste empréstimo.");
        }

        Jogador jogadorDestino = emprestimo.getJogadorDestino();
        Jogador jogadorOrigem = emprestimo.getJogadorOrigem();

        if (jogadorDestino.getSaldo() < valor) {
            throw new RegraNegocialException("Saldo insuficiente para realizar o pagamento.");
        }

        if (valor > emprestimo.getSaldoDevedor()) {
            throw new RegraNegocialException("O valor do pagamento excede o saldo devedor.");
        }

        // Atualiza os saldos
        jogadorService.debitarSaldo(jogadorDestino.getId(), valor);
        jogadorService.creditarSaldo(jogadorOrigem.getId(), valor);

        // Atualiza o saldo devedor
        emprestimo.setSaldoDevedor(emprestimo.getSaldoDevedor() - valor);

        // Se o saldo for quitado, encerra o empréstimo
        if (emprestimo.getSaldoDevedor() == 0) {
            emprestimo.setStatus(StatusEmprestimo.ENCERRADO);
        }

        emprestimoRepository.save(emprestimo);
    }


    public boolean existsByJogadorDestinoAndStatus(Jogador jogador, StatusEmprestimo statusEmprestimo) {
        return emprestimoRepository.existsByJogadorDestinoAndStatus(jogador, statusEmprestimo);
    }
}

