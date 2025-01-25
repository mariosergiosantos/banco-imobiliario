package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.VencedorJogoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.service.JogoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jogo")
public class JogoController {

    private static final Logger log = LoggerFactory.getLogger(JogoController.class);

    @Autowired
    private JogoService jogoService;

    @PostMapping("/salas/{id}/iniciar")
    @ResponseStatus(HttpStatus.OK)
    public void iniciarJogo(@PathVariable String id)
            throws ResourceNotFoundException, RegraNegocialException {
        log.info("Iniciando jogo da sala {}", id);

        jogoService.iniciar(id);

        log.info("Jogo da sala {} iniciado com sucesso", id);
    }

    @PostMapping("/salas/{id}/finalizar")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VencedorJogoDto> finalizarJogo(@PathVariable String id)
            throws ResourceNotFoundException, RegraNegocialException {
        log.info("Finalizando jogo da sala {}", id);

        VencedorJogoDto vencedorJogoDto = jogoService.finalizar(id);

        log.info("Jogo da sala {} finalizado com sucesso", id);

        return ResponseEntity.ok(vencedorJogoDto);
    }


}
