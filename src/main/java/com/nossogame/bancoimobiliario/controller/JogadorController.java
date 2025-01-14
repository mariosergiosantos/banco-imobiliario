package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.JogadorDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.service.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jogadores")
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

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

}

