package com.nossogame.bancoimobiliario.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_propriedade", discriminatorType = DiscriminatorType.STRING)
public abstract class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    @Column(nullable = false)
    protected String nome;

    @Column(nullable = false)
    protected double valorCompra;

    @Column(nullable = false)
    protected double aluguelBase;

    @Column(nullable = false)
    protected boolean hipotecada;

    @Column(nullable = false)
    protected double valorAluguelAtual;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    protected Jogador dono;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    protected Sala sala;

    public Propriedade() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public double getAluguelBase() {
        return aluguelBase;
    }

    public void setAluguelBase(double aluguelBase) {
        this.aluguelBase = aluguelBase;
    }

    public double getValorAluguelAtual() {
        return valorAluguelAtual;
    }

    public void setValorAluguelAtual(double valorAluguelAtual) {
        this.valorAluguelAtual = valorAluguelAtual;
    }

    public boolean isHipotecada() {
        return hipotecada;
    }

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }

    public Jogador getDono() {
        return dono;
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
