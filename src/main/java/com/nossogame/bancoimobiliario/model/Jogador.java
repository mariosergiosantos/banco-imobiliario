package com.nossogame.bancoimobiliario.model;

import com.nossogame.bancoimobiliario.model.enuns.StatusJogador;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private double saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    /*@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusJogador status;*/

    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Propriedade> propriedades;

    @Column(nullable = false)
    private boolean isAdmin;

    @Transient
    private double saldoPropriedades;

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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /*public StatusJogador getStatus() {
        return status;
    }

    public void setStatus(StatusJogador status) {
        this.status = status;
    }*/

    public List<Propriedade> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(List<Propriedade> propriedades) {
        this.propriedades = propriedades;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public double getSaldoPropriedades() {
        return saldoPropriedades;
    }

    public void setSaldoPropriedades(double saldoPropriedades) {
        this.saldoPropriedades = saldoPropriedades;
    }
}
