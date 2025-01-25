package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.enuns.StatusJogador;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FalenciaService {

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private TransacaoService transacaoService;

    @Transactional
    public void declararFalencia(Jogador jogador, Jogador credor) {
        // Transferir propriedades para o credor ou para o banco
        List<Propriedade> propriedades = propriedadeService.buscarPropriedadesPorJogador(jogador);
        for (Propriedade propriedade : propriedades) {
            if (credor != null) {
                propriedade.setDono(credor);
            } else {
                propriedade.setDono(null); // Volta para o banco
            }
            propriedade.setHipotecada(false); // Remove hipoteca ao retornar ao banco
            propriedadeService.atualizarPropriedade(propriedade);
        }

        // Atualizar o status do jogador
        //jogador.setStatus(StatusJogador.FALIDO);
        jogador.setSaldo(0);
        jogadorService.atualizarJogador(jogador);

        // TODO Registrar transação
        //transacaoService.registrarTransacaoFalencia(jogador, credor, propriedades);
    }
}

