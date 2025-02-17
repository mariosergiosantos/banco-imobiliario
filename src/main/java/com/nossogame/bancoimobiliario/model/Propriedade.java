package com.nossogame.bancoimobiliario.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_propriedade", discriminatorType = DiscriminatorType.STRING)
public abstract class Propriedade extends AbstractModel {

    @Column(nullable = false)
    protected String nome;

    @Column(nullable = false)
    protected double valorCompra;

    @Column(nullable = false)
    protected boolean hipotecada;

    @Column(nullable = false)
    private boolean companhia;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    protected Jogador dono;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    protected Sala sala;

    public Propriedade() {
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

    public boolean isHipotecada() {
        return hipotecada;
    }

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }

    public boolean isCompanhia() {
        return companhia;
    }

    public void setCompanhia(boolean companhia) {
        this.companhia = companhia;
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
