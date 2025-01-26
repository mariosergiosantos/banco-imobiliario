package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.SalaDto;
import com.nossogame.bancoimobiliario.dto.VencedorJogoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.service.JogoService;
import com.nossogame.bancoimobiliario.service.SalaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
public class SalaController {

    private static final Logger log = LoggerFactory.getLogger(SalaController.class);

    @Autowired
    private JogoService jogoService;

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

    @PostMapping("/{salaId}/iniciar")
    @ResponseStatus(HttpStatus.OK)
    public void iniciarJogo(@PathVariable String salaId)
            throws ResourceNotFoundException, RegraNegocialException {
        log.info("Iniciando jogo da sala {}", salaId);

        jogoService.iniciar(salaId);

        log.info("Jogo da sala {} iniciado com sucesso", salaId);
    }

    @PostMapping("/{salaId}/finalizar")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VencedorJogoDto> finalizarJogo(@PathVariable String salaId)
            throws ResourceNotFoundException, RegraNegocialException {
        log.info("Finalizando jogo da sala {}", salaId);

        VencedorJogoDto vencedorJogoDto = jogoService.finalizar(salaId);

        log.info("Jogo da sala {} finalizado com sucesso", salaId);

        return ResponseEntity.ok(vencedorJogoDto);
    }
}

