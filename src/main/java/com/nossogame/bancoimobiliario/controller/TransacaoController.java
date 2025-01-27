package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.TransacaoDto;
import com.nossogame.bancoimobiliario.dto.request.TransacaoRequestDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/salas/{salaId}")
    public ResponseEntity<TransacaoDto> realizarTransacao(
            @PathVariable String salaId,
            @RequestBody @Valid TransacaoRequestDto transacaoRequest)
            throws ResourceNotFoundException, RegraNegocialException {
        return ResponseEntity.ok(transacaoService.registrarTransacao(salaId, transacaoRequest));
    }

    /*@PostMapping("/salas/{salaId}/propriedades/{propriedadeId}/construir")
    public ResponseEntity<TransacaoDto> construirPropriedade(
            @PathVariable String salaId,
            @PathVariable String propriedadeId,
            @RequestParam String jogadorId)
            throws ResourceNotFoundException, RegraNegocialException {
        return ResponseEntity.ok(transacaoService.construirPropriedade(salaId, propriedadeId, jogadorId));
    }*/

    @GetMapping("/salas/{id}")
    public ResponseEntity<List<TransacaoDto>> listarTransacoesDaSala(@PathVariable String id) {
        return ResponseEntity.ok(transacaoService.listarTransacoesDaSala(id));
    }

}
