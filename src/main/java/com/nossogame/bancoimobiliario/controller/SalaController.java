package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.SalaDto;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<SalaDto> criarSala() {
        return new ResponseEntity(salaService.criarSala(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscarSala(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(salaService.buscarSalaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<SalaDto>> listarTodasSalas() {
        return ResponseEntity.ok(salaService.listarTodasSalas());
    }
}

