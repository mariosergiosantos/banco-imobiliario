package com.nossogame.bancoimobiliario.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jogador_id", nullable = false)
    private Jogador jogador;

    @Column(nullable = false)
    private double saldoFinal;

    @Column(nullable = false)
    private int numeroPropriedades;

    @Column(nullable = false)
    private LocalDateTime dataVitoria;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public int getNumeroPropriedades() {
        return numeroPropriedades;
    }

    public void setNumeroPropriedades(int numeroPropriedades) {
        this.numeroPropriedades = numeroPropriedades;
    }

    public LocalDateTime getDataVitoria() {
        return dataVitoria;
    }

    public void setDataVitoria(LocalDateTime dataVitoria) {
        this.dataVitoria = dataVitoria;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
