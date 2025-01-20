package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.ConstruirPropriedadeRequestDto;
import com.nossogame.bancoimobiliario.dto.TransacaoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.factory.TransacaoFactory;
import com.nossogame.bancoimobiliario.mapper.TransacaoMapper;
import com.nossogame.bancoimobiliario.model.*;
import com.nossogame.bancoimobiliario.model.enuns.CorPropriedade;
import com.nossogame.bancoimobiliario.repository.PropriedadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private JogadorService jogadorService;

    public Propriedade atualizarPropriedade(Propriedade propriedade) {
        return propriedadeRepository.save(propriedade);
    }

    public Propriedade buscarPropriedade(String propriedadeId, String salaId) throws ResourceNotFoundException {
        return propriedadeRepository.findByIdAndSalaId(propriedadeId, salaId)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada"));
    }

    public void cadastrarPropriedadesBase(Sala sala) {

        List<Propriedade> propriedades = Arrays.asList(
                // Roxo
                new Casa("Leblon", CorPropriedade.ROXO, 100, 6, sala),
                new Casa("Avenida Presidente Vargas", CorPropriedade.ROXO, 120, 8, sala),

                // Ciano
                new Casa("Avenida Nossa Senhora de Copacabana", CorPropriedade.CIANO, 140, 10, sala),
                new Casa("Avenida Brigadeiro Faria Lima", CorPropriedade.CIANO, 160, 12, sala),
                new Casa("Avenida Rebouças", CorPropriedade.CIANO, 180, 14, sala),

                // Rosa
                new Casa("Avenida 9 de Julho", CorPropriedade.ROSA, 200, 16, sala),
                new Casa("Avenida Europa", CorPropriedade.ROSA, 220, 18, sala),
                new Casa("Rua Augusta", CorPropriedade.ROSA, 240, 20, sala),

                // Laranja
                new Casa("Avenida Interlagos", CorPropriedade.LARANJA, 260, 22, sala),
                new Casa("Morumbi", CorPropriedade.LARANJA, 280, 24, sala),
                new Casa("Vila Mariana", CorPropriedade.LARANJA, 300, 26, sala),

                // Vermelho
                new Casa("Avenida Paulista", CorPropriedade.VERMELHO, 320, 28, sala),
                new Casa("Avenida Brigadeiro Luís Antônio", CorPropriedade.VERMELHO, 340, 30, sala),
                new Casa("Jardins", CorPropriedade.VERMELHO, 360, 32, sala),

                // Amarelo
                new Casa("Copacabana", CorPropriedade.AMARELO, 380, 34, sala),
                new Casa("Ipanema", CorPropriedade.AMARELO, 400, 36, sala),
                new Casa("Barra da Tijuca", CorPropriedade.AMARELO, 420, 38, sala),

                // Verde
                new Casa("Avenida Brasil", CorPropriedade.VERDE, 450, 40, sala),
                new Casa("Avenida Dom Pedro II", CorPropriedade.VERDE, 470, 42, sala),
                new Casa("Avenida Getúlio Vargas", CorPropriedade.VERDE, 490, 44, sala),

                // Azul
                new Casa("Avenida Atlântica", CorPropriedade.AZUL, 500, 50, sala),
                new Casa("Avenida Boa Viagem", CorPropriedade.AZUL, 600, 60, sala),

                // Companhias
                new Companhia("Companhia de Eletricidade", 150, 0, sala),
                new Companhia("Companhia de Água", 150, 0, sala),

                // Estações
                new Companhia("Estação da Luz", 200, 25, sala),
                new Companhia("Estação Júlio Prestes", 200, 25, sala),
                new Companhia("Estação da Sé", 200, 25, sala),
                new Companhia("Estação Vila Mariana", 200, 25, sala)
        );


        propriedadeRepository.saveAll(propriedades);
    }

    //TODO adicionar validação para que todas as casas tenham a mesma quantidade de propriedade
    private boolean verificarPropriedadesDaMesmaCor(String salaId, CorPropriedade cor, Jogador jogador) {
        List<Casa> propriedades = propriedadeRepository.findBySalaIdAndCor(salaId, cor);
        return propriedades.stream()
                .allMatch(propriedade ->
                        propriedade.getDono() != null && jogador.getId().equals(propriedade.getDono().getId())
                );
    }

    public double calcularValorTotalPropriedades(Jogador jogador) {
        return propriedadeRepository.findByDono(jogador)
                .stream()
                .mapToDouble(Propriedade::getValorCompra)
                .sum();
    }

    @Transactional
    public TransacaoDto construirPropriedade(String salaId, ConstruirPropriedadeRequestDto requestDto)
            throws ResourceNotFoundException, RegraNegocialException {

        Propriedade propriedade = propriedadeRepository.findById(requestDto.getPropriedadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada."));


        Jogador jogador = jogadorService.findById(requestDto.getJogadorId());

        if (!(propriedade instanceof Casa)) {
            throw new RegraNegocialException("Apenas propriedades do tipo Casa permitem construção.");
        }

        Casa casa = (Casa) propriedade;

        double custoConstrucao = calcularCustoConstrucao(casa);
        if (jogador.getSaldo() < custoConstrucao) {
            throw new RegraNegocialException("Saldo insuficiente para construir.");
        }

        if (casa.isHipotecada()) {
            throw new RegraNegocialException("Não é possível construir em propriedades hipotecadas.");
        }

        if (casa.isHotel()) {
            throw new RegraNegocialException("A propriedade já atingiu o limite de construções.");
        }

        boolean todasPropriedadesPossuidas = verificarPropriedadesDaMesmaCor(salaId, casa.getCor(), jogador);
        if (!todasPropriedadesPossuidas) {
            throw new RegraNegocialException("Apenas propriedades do tipo Casa permitem construção.");
        }

        jogadorService.debitarSaldo(jogador.getId(), custoConstrucao);

        casa.setNumeroCasas(casa.getNumeroCasas() + 1);

        if (casa.getNumeroCasas() == 4) {
            casa.setHotel(true);
            casa.setNumeroCasas(0);
        }

        propriedadeRepository.save(casa);

        Transacao transacao = TransacaoFactory.criarTransacaoContruirPropriedade(propriedade.getSala(), jogador, propriedade, custoConstrucao, "Construção de propriedade");

        // TODO salvar transacao
        return TransacaoMapper.INSTANCE.toDTO(transacao);
    }

    private double calcularCustoConstrucao(Casa casa) {
        return casa.getValorCompra() * (casa.getNumeroCasas() + 1);
    }

    public List<Propriedade> buscarPropriedadesPorJogador(Jogador jogador) {
        return propriedadeRepository.findByDono(jogador);
    }
}
