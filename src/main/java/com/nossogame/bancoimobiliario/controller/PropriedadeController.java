package com.nossogame.bancoimobiliario.controller;

import com.nossogame.bancoimobiliario.dto.ConstruirPropriedadeRequestDto;
import com.nossogame.bancoimobiliario.dto.TransacaoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.service.PropriedadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/propriedades")
public class PropriedadeController {

    @Autowired
    private PropriedadeService propriedadeService;

    @PostMapping("/salas/{salaId}/propriedades/{propriedadeId}/construir")
    public ResponseEntity<TransacaoDto> construirPropriedade(
            @PathVariable String salaId,
            @PathVariable String propriedadeId,
            @RequestBody ConstruirPropriedadeRequestDto requestDto) throws ResourceNotFoundException, RegraNegocialException {
        return ResponseEntity.ok(propriedadeService.construirPropriedade(salaId, propriedadeId, requestDto));
    }


}
