package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.PagarEmprestimoDto;
import com.nossogame.bancoimobiliario.dto.SolicitarEmprestimoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Emprestimo;
import com.nossogame.bancoimobiliario.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<Emprestimo> solicitarEmprestimo(@Valid @RequestBody SolicitarEmprestimoDto dto) throws RegraNegocialException, ResourceNotFoundException {
        Emprestimo emprestimo = emprestimoService.solicitarEmprestimo(
                dto.getJogadorOrigemId(),
                dto.getJogadorDestinoId(),
                dto.getValorContratado(),
                dto.getValorAcordado()
        );
        return ResponseEntity.ok(emprestimo);
    }

    @PostMapping("/pagamento")
    public ResponseEntity<String> pagarEmprestimo(@Valid @RequestBody PagarEmprestimoDto dto)
            throws RegraNegocialException, ResourceNotFoundException {
        emprestimoService.pagarEmprestimo(
                dto.getEmprestimoId(),
                dto.getJogadorDestinoId(),
                dto.getValor()
        );
        return ResponseEntity.ok("Pagamento realizado com sucesso.");
    }
}

