package com.nossogame.bancoimobiliario.model;

import com.nossogame.bancoimobiliario.model.enuns.CorPropriedade;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "propriedade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    protected List<AluguelPropriedade> alugueis;

    public Casa() {
        super();
    }

    public Casa(String nome, CorPropriedade corPropriedade, int valorCompra, Sala sala, List<AluguelPropriedade> aluguelPropriedades) {
        this.nome = nome;
        this.cor = corPropriedade;
        this.valorCompra = valorCompra;
        this.sala = sala;
        setAlugueis(aluguelPropriedades);
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

    public List<AluguelPropriedade> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(List<AluguelPropriedade> alugueis) {
        if (Objects.isNull(this.alugueis)) {
            this.alugueis = new ArrayList<>();
        }
        this.alugueis.addAll(alugueis);
        alugueis.forEach(aluguel -> aluguel.setPropriedade(this));
    }
}
