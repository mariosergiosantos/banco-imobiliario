package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.*;
import com.nossogame.bancoimobiliario.model.enuns.CorPropriedade;
import com.nossogame.bancoimobiliario.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.nossogame.bancoimobiliario.model.enuns.TipoAluguel.*;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

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
                new Casa("Avenida São João", CorPropriedade.ROXO, 50000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 8000),
                        new AluguelPropriedade(CASA_1, 40000),
                        new AluguelPropriedade(CASA_2, 100000),
                        new AluguelPropriedade(CASA_3, 300000),
                        new AluguelPropriedade(CASA_4, 450000),
                        new AluguelPropriedade(HOTEL, 600000)
                )),
                new Casa("Avenida Ipiranga", CorPropriedade.ROXO, 50000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 6000),
                        new AluguelPropriedade(CASA_1, 30000),
                        new AluguelPropriedade(CASA_2, 90000),
                        new AluguelPropriedade(CASA_3, 270000),
                        new AluguelPropriedade(CASA_4, 400000),
                        new AluguelPropriedade(HOTEL, 500000)
                )),

                // Ciano
                new Casa("Rua da Consolação", CorPropriedade.CIANO, 100000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 14000),
                        new AluguelPropriedade(CASA_1, 70000),
                        new AluguelPropriedade(CASA_2, 200000),
                        new AluguelPropriedade(CASA_3, 550000),
                        new AluguelPropriedade(CASA_4, 750000),
                        new AluguelPropriedade(HOTEL, 950000)
                )),
                new Casa("Viaduto do Chá", CorPropriedade.CIANO, 100000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 16000),
                        new AluguelPropriedade(CASA_1, 80000),
                        new AluguelPropriedade(CASA_2, 220000),
                        new AluguelPropriedade(CASA_3, 600000),
                        new AluguelPropriedade(CASA_4, 800000),
                        new AluguelPropriedade(HOTEL, 1000000)
                )),
                new Casa("Praça da Sé", CorPropriedade.CIANO, 100000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 14000),
                        new AluguelPropriedade(CASA_1, 70000),
                        new AluguelPropriedade(CASA_2, 200000),
                        new AluguelPropriedade(CASA_3, 550000),
                        new AluguelPropriedade(CASA_4, 750000),
                        new AluguelPropriedade(HOTEL, 950000)
                )),

                // Rosa
                new Casa("Higienópolis", CorPropriedade.ROSA, 200000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 50000),
                        new AluguelPropriedade(CASA_1, 200000),
                        new AluguelPropriedade(CASA_2, 600000),
                        new AluguelPropriedade(CASA_3, 1400000),
                        new AluguelPropriedade(CASA_4, 1700000),
                        new AluguelPropriedade(HOTEL, 2000000)
                )),
                new Casa("Jardins", CorPropriedade.ROSA, 200000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 35000),
                        new AluguelPropriedade(CASA_1, 175000),
                        new AluguelPropriedade(CASA_2, 500000),
                        new AluguelPropriedade(CASA_3, 1100000),
                        new AluguelPropriedade(CASA_4, 1300000),
                        new AluguelPropriedade(HOTEL, 1500000)
                )),

                // Laranja
                new Casa("Av. do Contorno", CorPropriedade.LARANJA, 200000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 26000),
                        new AluguelPropriedade(CASA_1, 130000),
                        new AluguelPropriedade(CASA_2, 390000),
                        new AluguelPropriedade(CASA_3, 900000),
                        new AluguelPropriedade(CASA_4, 1100000),
                        new AluguelPropriedade(HOTEL, 1275000)
                )),
                new Casa("Praça dos Três Poderes", CorPropriedade.LARANJA, 200000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 28000),
                        new AluguelPropriedade(CASA_1, 150000),
                        new AluguelPropriedade(CASA_2, 450000),
                        new AluguelPropriedade(CASA_3, 1000000),
                        new AluguelPropriedade(CASA_4, 1200000),
                        new AluguelPropriedade(HOTEL, 1400000)
                )),
                new Casa("Praça Castro Alves", CorPropriedade.LARANJA, 200000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 26000),
                        new AluguelPropriedade(CASA_1, 130000),
                        new AluguelPropriedade(CASA_2, 390000),
                        new AluguelPropriedade(CASA_3, 900000),
                        new AluguelPropriedade(CASA_4, 1100000),
                        new AluguelPropriedade(HOTEL, 1275000)
                )),

                // Vermelho
                new Casa("Avenida Ibirapuera", CorPropriedade.VERMELHO, 150000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 18000),
                        new AluguelPropriedade(CASA_1, 90000),
                        new AluguelPropriedade(CASA_2, 250000),
                        new AluguelPropriedade(CASA_3, 700000),
                        new AluguelPropriedade(CASA_4, 875000),
                        new AluguelPropriedade(HOTEL, 1050000)
                )),
                new Casa("Avenida Juscelino Kubitschek", CorPropriedade.VERMELHO, 150000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 18000),
                        new AluguelPropriedade(CASA_1, 90000),
                        new AluguelPropriedade(CASA_2, 250000),
                        new AluguelPropriedade(CASA_3, 700000),
                        new AluguelPropriedade(CASA_4, 875000),
                        new AluguelPropriedade(HOTEL, 1050000)
                )),
                new Casa("Rua Oscar Freire", CorPropriedade.VERMELHO, 150000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 20000),
                        new AluguelPropriedade(CASA_1, 100000),
                        new AluguelPropriedade(CASA_2, 300000),
                        new AluguelPropriedade(CASA_3, 750000),
                        new AluguelPropriedade(CASA_4, 925000),
                        new AluguelPropriedade(HOTEL, 1100000)
                )),

                // Amarelo
                new Casa("Ponte Rio-Niterói", CorPropriedade.AMARELO, 150000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 8000),
                        new AluguelPropriedade(CASA_1, 40000),
                        new AluguelPropriedade(CASA_2, 100000),
                        new AluguelPropriedade(CASA_3, 300000),
                        new AluguelPropriedade(CASA_4, 450000),
                        new AluguelPropriedade(HOTEL, 600000)
                )),
                new Casa("Marina Glória", CorPropriedade.AMARELO, 15000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 8000),
                        new AluguelPropriedade(CASA_1, 40000),
                        new AluguelPropriedade(CASA_2, 100000),
                        new AluguelPropriedade(CASA_3, 300000),
                        new AluguelPropriedade(CASA_4, 450000),
                        new AluguelPropriedade(HOTEL, 600000)
                )),
                new Casa("Barra do Tijuca", CorPropriedade.AMARELO, 150000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 8000),
                        new AluguelPropriedade(CASA_1, 40000),
                        new AluguelPropriedade(CASA_2, 100000),
                        new AluguelPropriedade(CASA_3, 300000),
                        new AluguelPropriedade(CASA_4, 450000),
                        new AluguelPropriedade(HOTEL, 600000)
                )),

                // Verde
                new Casa("Jardim Botânico", CorPropriedade.VERDE, 50000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 4000),
                        new AluguelPropriedade(CASA_1, 20000),
                        new AluguelPropriedade(CASA_2, 60000),
                        new AluguelPropriedade(CASA_3, 180000),
                        new AluguelPropriedade(CASA_4, 320000),
                        new AluguelPropriedade(HOTEL, 450000)
                )),
                new Casa("Av. Beira Mar", CorPropriedade.VERDE, 50000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 6000),
                        new AluguelPropriedade(CASA_1, 30000),
                        new AluguelPropriedade(CASA_2, 90000),
                        new AluguelPropriedade(CASA_3, 270000),
                        new AluguelPropriedade(CASA_4, 400000),
                        new AluguelPropriedade(HOTEL, 500000)
                )),
                new Casa("Av. Niemeyer", CorPropriedade.VERDE, 50000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 2000),
                        new AluguelPropriedade(CASA_1, 10000),
                        new AluguelPropriedade(CASA_2, 30000),
                        new AluguelPropriedade(CASA_3, 90000),
                        new AluguelPropriedade(CASA_4, 160000),
                        new AluguelPropriedade(HOTEL, 250000)
                )),

                // Azul
                new Casa("Avenida Paulista", CorPropriedade.AZUL, 100000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 8000),
                        new AluguelPropriedade(CASA_1, 40000),
                        new AluguelPropriedade(CASA_2, 100000),
                        new AluguelPropriedade(CASA_3, 300000),
                        new AluguelPropriedade(CASA_4, 450000),
                        new AluguelPropriedade(HOTEL, 600000)
                )),
                new Casa("Avenida Recife", CorPropriedade.AZUL, 100000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 8000),
                        new AluguelPropriedade(CASA_1, 40000),
                        new AluguelPropriedade(CASA_2, 100000),
                        new AluguelPropriedade(CASA_3, 300000),
                        new AluguelPropriedade(CASA_4, 450000),
                        new AluguelPropriedade(HOTEL, 600000)
                )),
                new Casa("Ponte do Guaíba", CorPropriedade.AZUL, 100000, sala, Arrays.asList(
                        new AluguelPropriedade(BASE, 8000),
                        new AluguelPropriedade(CASA_1, 40000),
                        new AluguelPropriedade(CASA_2, 100000),
                        new AluguelPropriedade(CASA_3, 300000),
                        new AluguelPropriedade(CASA_4, 450000),
                        new AluguelPropriedade(HOTEL, 600000)
                )),

                // Companhias
                new Companhia("Companhia de Força e Luz", 200000, sala),
                new Companhia("Companhia de Mineração", 20000, sala),
                new Companhia("Companhia de Água e Saneamento", 20000, sala),
                new Companhia("PontoCom", 150000, sala),
                new Companhia("Créditos de Carbono", 150000, sala),
                new Companhia("Compahia Petrolífera", 200000, sala)
        );


        propriedadeRepository.saveAll(propriedades);
    }

    public double calcularValorTotalPropriedades(Jogador jogador) {
        return propriedadeRepository.findByDono(jogador)
                .stream()
                .mapToDouble(Propriedade::getValorCompra)
                .sum();
    }

    public List<Propriedade> buscarPropriedadesPorJogador(Jogador jogador) {
        return propriedadeRepository.findByDono(jogador);
    }
}
