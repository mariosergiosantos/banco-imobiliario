package com.nossogame.bancoimobiliario.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COMPANHIA")
public class Companhia extends Propriedade {

    public Companhia() {

    }

    public Companhia(String nome, int valorCompra, Sala sala) {
        this.nome = nome;
        this.valorCompra = valorCompra;
        this.sala = sala;
        this.setCompanhia(true);
    }
}
