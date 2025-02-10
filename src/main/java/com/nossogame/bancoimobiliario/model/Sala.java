package com.nossogame.bancoimobiliario.model;

import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Sala {

    @Id
    @Column(length = 7, nullable = false, unique = true)
    private String id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSala status;

    @OneToOne
    @JoinColumn(name = "administrador_id")
    private Jogador administrador;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Jogador> jogadores;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Propriedade> propriedades;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusSala getStatus() {
        return status;
    }

    public void setStatus(StatusSala status) {
        this.status = status;
    }

    public Jogador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Jogador administrador) {
        this.administrador = administrador;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public void addJogador(Jogador jogador) {
        if (this.jogadores == null) {
            this.jogadores = List.of(jogador);
            return;
        }
        this.jogadores.add(jogador);
    }

    public List<Propriedade> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(List<Propriedade> propriedades) {
        this.propriedades = propriedades;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
}
