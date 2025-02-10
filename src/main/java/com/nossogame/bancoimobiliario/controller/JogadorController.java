package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.request.CartaImpactoRequestDto;
import com.nossogame.bancoimobiliario.dto.JogadorDto;
import com.nossogame.bancoimobiliario.dto.request.JogadorRequestDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.service.JogadorService;
import com.nossogame.bancoimobiliario.service.SorteRevesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jogadores")
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private SorteRevesService sorteRevesService;

    @PostMapping
    public ResponseEntity<JogadorDto> adicionarJogador(@Valid @RequestBody JogadorRequestDto jogador)
            throws ResourceNotFoundException, RegraNegocialException {
        JogadorDto jogadorDto = jogadorService.adicionarJogador(jogador.salaId(), jogador);
        return new ResponseEntity<>(jogadorDto, HttpStatus.CREATED);
    }

    @GetMapping("/salas/{salaId}")
    public ResponseEntity<List<JogadorDto>> listarJogadoresDaSala(@PathVariable String salaId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(jogadorService.listarJogadoresDaSala(salaId));
    }

    @PostMapping("/{jogadorId}/sorte-reves")
    public ResponseEntity<Void> aplicarImpacto(@PathVariable String jogadorId, @RequestBody CartaImpactoRequestDto impacto)
            throws ResourceNotFoundException {
        sorteRevesService.aplicarImpacto(jogadorId, impacto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

