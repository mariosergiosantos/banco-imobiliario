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

import java.util.List;

@RestController
@RequestMapping("/api/v1/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDto> buscarEmprestimo(@PathVariable String id) throws ResourceNotFoundException {
        EmprestimoDto emprestimo = emprestimoService.buscarEmprestimoPorId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping("/salas/{salaId}")
    public ResponseEntity<List<EmprestimoDto>> listarEmprestimosPorSala(@PathVariable String salaId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<EmprestimoDto> solicitarEmprestimo(@Valid @RequestBody SolicitarEmprestimoDto dto) throws RegraNegocialException, ResourceNotFoundException {
        EmprestimoDto emprestimo = emprestimoService.solicitarEmprestimo(dto);
        return new ResponseEntity<>(emprestimo, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/pagamento")
    public ResponseEntity<Void> pagarEmprestimo(@PathVariable String id, @Valid @RequestBody PagarEmprestimoDto dto)
            throws RegraNegocialException, ResourceNotFoundException {
        emprestimoService.pagarEmprestimo(
                id,
                dto.getPagadorId(),
                dto.getValor()
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

