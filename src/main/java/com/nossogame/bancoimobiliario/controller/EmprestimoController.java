package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.EmprestimoDto;
import com.nossogame.bancoimobiliario.dto.PagarEmprestimoDto;
import com.nossogame.bancoimobiliario.dto.SolicitarEmprestimoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDto> buscarEmprestimo(@PathVariable String id) throws RegraNegocialException, ResourceNotFoundException {
        EmprestimoDto emprestimo = emprestimoService.buscarEmprestimoPorId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @PostMapping("/salas/{id}")
    public ResponseEntity<EmprestimoDto> solicitarEmprestimo(@PathVariable String id, @Valid @RequestBody SolicitarEmprestimoDto dto) throws RegraNegocialException, ResourceNotFoundException {
        EmprestimoDto emprestimo = emprestimoService.solicitarEmprestimo(dto);
        return new ResponseEntity<>(emprestimo, HttpStatus.CREATED);
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

