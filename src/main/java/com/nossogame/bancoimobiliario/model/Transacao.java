package com.nossogame.bancoimobiliario.model;

import com.nossogame.bancoimobiliario.model.enuns.TipoTransacao;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transacao extends AbstractModel {

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "origem_id")
    private Jogador vendedor;

    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Jogador comprador;

    @ManyToOne
    @JoinColumn(name = "propriedade_id")
    private Propriedade propriedade;

    @Column(nullable = false)
    private double valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipo;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime dataHora;

    @Column
    private String descricao;

    public Transacao() {
    }

    public Transacao(Sala sala, Jogador comprador, Jogador vendedor, Propriedade propriedade, TipoTransacao tipo, double valor, String descricao) {
        this.sala = sala;
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.propriedade = propriedade;
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Jogador getVendedor() {
        return vendedor;
    }

    public void setVendedor(Jogador vendedor) {
        this.vendedor = vendedor;
    }

    public Jogador getComprador() {
        return comprador;
    }

    public void setComprador(Jogador comprador) {
        this.comprador = comprador;
    }

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
