package com.nossogame.bancoimobiliario.model;

import com.nossogame.bancoimobiliario.model.enuns.TipoAluguel;
import jakarta.persistence.*;

@Entity
public class AluguelPropriedade extends AbstractModel {

    @ManyToOne
    @JoinColumn(name = "propriedade_id", nullable = false)
    private Propriedade propriedade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAluguel tipoAluguel;

    @Column(nullable = false)
    private double valor;

    public AluguelPropriedade() {
    }

    public AluguelPropriedade(Propriedade propriedade, TipoAluguel tipoAluguel, double valor) {
        this.propriedade = propriedade;
        this.tipoAluguel = tipoAluguel;
        this.valor = valor;
    }

    public AluguelPropriedade(TipoAluguel tipoAluguel, double valor) {
        this.tipoAluguel = tipoAluguel;
        this.valor = valor;
    }

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public TipoAluguel getTipoAluguel() {
        return tipoAluguel;
    }

    public void setTipoAluguel(TipoAluguel tipoAluguel) {
        this.tipoAluguel = tipoAluguel;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}