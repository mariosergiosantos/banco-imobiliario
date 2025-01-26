package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.EmprestimoDto;
import com.nossogame.bancoimobiliario.dto.SolicitarEmprestimoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.mapper.EmprestimoMapper;
import com.nossogame.bancoimobiliario.model.Emprestimo;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
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

    @Transactional
    public EmprestimoDto solicitarEmprestimo(SolicitarEmprestimoDto dto) throws RegraNegocialException, ResourceNotFoundException {
        Jogador pagador = jogadorService.findById(dto.getPagadorId());

        if (pagador.getSaldo() < dto.getValorContratado()) {
            throw new RegraNegocialException("O jogador não possui saldo suficiente para conceder o empréstimo.");
        }

        Jogador recebedor = jogadorService.findById(dto.getRecebedorId());

        if (dto.getValorAcordado() < dto.getValorContratado()) {
            throw new RegraNegocialException("O valor acordado não pode ser menor que o valor contratado.");
        }

        jogadorService.debitarSaldo(pagador, dto.getValorContratado());
        jogadorService.creditarSaldo(recebedor, dto.getValorAcordado());

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setRecebedor(recebedor);
        emprestimo.setPagador(pagador);
        emprestimo.setValorContratado(dto.getValorContratado());
        emprestimo.setValorDevolucao(dto.getValorAcordado());
        emprestimo.setSaldoDevedor(dto.getValorAcordado());
        emprestimo.setStatus(StatusEmprestimo.PENDENTE);
        emprestimo.setDataEmprestimo(LocalDateTime.now());

        //TODO registra a transação
        return EmprestimoMapper.INSTANCE.toDTO(emprestimoRepository.save(emprestimo));
    }


    @Transactional
    public EmprestimoDto pagarEmprestimo(String emprestimoId, String jogadorDestinoId, double valor) throws ResourceNotFoundException, RegraNegocialException {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado."));

        if (emprestimo.getStatus().equals(StatusEmprestimo.ENCERRADO)) {
            throw new RegraNegocialException("O empréstimo já foi encerrado.");
        }

        if (!emprestimo.getPagador().getId().equals(jogadorDestinoId)) {
            throw new RegraNegocialException("O jogador informado não é o devedor deste empréstimo.");
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

        // TODO registra a transação
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

