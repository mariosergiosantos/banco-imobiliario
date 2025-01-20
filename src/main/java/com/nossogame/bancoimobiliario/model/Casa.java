package com.nossogame.bancoimobiliario.model;

import com.nossogame.bancoimobiliario.model.enuns.CorPropriedade;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CASA")
public class Casa extends Propriedade {

    @Enumerated(EnumType.STRING)
    @Column
    private CorPropriedade cor;

    @Column(nullable = false)
    private int numeroCasas;

    @Column(nullable = false)
    private boolean hotel;

    public Casa() {
        super();
    }

    public Casa(String nome, CorPropriedade corPropriedade, int valorCompra, int aluguelBase, Sala sala) {
        this.nome = nome;
        this.cor = corPropriedade;
        this.valorCompra = valorCompra;
        this.aluguelBase = aluguelBase;
        this.sala = sala;
    }

    public CorPropriedade getCor() {
        return cor;
    }

    public void setCor(CorPropriedade cor) {
        this.cor = cor;
    }

    public int getNumeroCasas() {
        return numeroCasas;
    }

    public void setNumeroCasas(int numeroCasas) {
        this.numeroCasas = numeroCasas;
    }

    public boolean isHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }
}
