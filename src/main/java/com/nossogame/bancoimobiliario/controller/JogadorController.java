package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.CartaImpactoRequestDto;
import com.nossogame.bancoimobiliario.dto.JogadorDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.service.JogadorService;
import com.nossogame.bancoimobiliario.service.SorteRevesService;
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

    @PostMapping("/salas/{id}")
    public ResponseEntity<JogadorDto> adicionarJogador(@PathVariable String id, @RequestBody Jogador jogador)
            throws ResourceNotFoundException, RegraNegocialException {
        return ResponseEntity.ok(jogadorService.adicionarJogador(id, jogador));
    }

    @GetMapping("/salas/{id}")
    public ResponseEntity<List<Jogador>> listarJogadoresDaSala(@PathVariable String id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(jogadorService.listarJogadoresDaSala(id));
    }

    @PostMapping("/salas/{id}/sorte-reves/aplicar")
    public ResponseEntity<Void> aplicarImpacto(@PathVariable String id, @RequestBody CartaImpactoRequestDto impacto)
            throws ResourceNotFoundException {
        sorteRevesService.aplicarImpacto(id, impacto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

