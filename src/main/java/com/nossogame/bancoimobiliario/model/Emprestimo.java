package com.nossogame.bancoimobiliario.model;

import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "jogador_origem_id", nullable = false)
    private Jogador recebedor;

    @ManyToOne
    @JoinColumn(name = "jogador_destino_id", nullable = false)
    private Jogador pagador;

    @Column(nullable = false)
    private double valorContratado;

    @Column(nullable = false)
    private double valorDevolucao;

    @Column(nullable = false)
    private double saldoDevedor;

    @Column
    private double taxaJuros;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEmprestimo status;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime dataEmprestimo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Jogador getRecebedor() {
        return recebedor;
    }

    public void setRecebedor(Jogador recebedor) {
        this.recebedor = recebedor;
    }

    public Jogador getPagador() {
        return pagador;
    }

    public void setPagador(Jogador pagador) {
        this.pagador = pagador;
    }

    public double getValorContratado() {
        return valorContratado;
    }

    public void setValorContratado(double valorContratado) {
        this.valorContratado = valorContratado;
    }

    public double getValorDevolucao() {
        return valorDevolucao;
    }

    public void setValorDevolucao(double valorDevolucao) {
        this.valorDevolucao = valorDevolucao;
    }

    public double getSaldoDevedor() {
        return saldoDevedor;
    }

    public void setSaldoDevedor(double saldoDevedor) {
        this.saldoDevedor = saldoDevedor;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }
}

