package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.ComprarPropriedadeBancoRequestDto;
import com.nossogame.bancoimobiliario.dto.ComprarPropriedadeJogadorRequestDto;
import com.nossogame.bancoimobiliario.dto.PagamentoAluguelRequestDto;
import com.nossogame.bancoimobiliario.dto.TransacaoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/salas/{salaId}/comprar/banco")
    public ResponseEntity<TransacaoDto> comprarPropriedadeBanco(
            @PathVariable String salaId,
            @RequestBody ComprarPropriedadeBancoRequestDto transacao)
            throws ResourceNotFoundException, RegraNegocialException {
        return ResponseEntity.ok(transacaoService.comprarPropriedadeBanco(salaId, transacao));
    }

    @PostMapping("/salas/{salaId}/comprar/jogador")
    public ResponseEntity<TransacaoDto> comprarPropriedadeJogador(
            @PathVariable String salaId,
            @RequestBody ComprarPropriedadeJogadorRequestDto transacao)
            throws ResourceNotFoundException, RegraNegocialException {
        return ResponseEntity.ok(transacaoService.comprarPropriedadeJogador(salaId, transacao));
    }

    @PostMapping("/salas/{salaId}/jogador/{jogadorId}/salario")
    public ResponseEntity<TransacaoDto> pagamentoSalario(
            @PathVariable String salaId,
            @PathVariable String jogadorId)
            throws ResourceNotFoundException, RegraNegocialException {
        return ResponseEntity.ok(transacaoService.pagamentoSalario(salaId, jogadorId));
    }

    @PostMapping("/salas/{salaId}/aluguel")
    public ResponseEntity<TransacaoDto> pagamentoAluguel(
            @PathVariable String salaId,
            @RequestBody PagamentoAluguelRequestDto transacao)
            throws ResourceNotFoundException, RegraNegocialException {
        return ResponseEntity.ok(transacaoService.pagamentoAluguel(salaId, transacao));
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
