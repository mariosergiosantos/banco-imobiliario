package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jogo")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @PostMapping("/salas/{id}/iniciar")
    @ResponseStatus(HttpStatus.OK)
    public void iniciarJogo(@PathVariable String id)
            throws ResourceNotFoundException, RegraNegocialException {
        jogoService.iniciar(id);
    }

    @PostMapping("/salas/{id}/finalizar")
    @ResponseStatus(HttpStatus.OK)
    public void finalizarJogo(@PathVariable String id)
            throws ResourceNotFoundException, RegraNegocialException {
        jogoService.finalizar(id);
    }


}
