package com.nossogame.bancoimobiliario.model;

import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "jogador_origem_id", nullable = false)
    private Jogador jogadorOrigem;

    @ManyToOne
    @JoinColumn(name = "jogador_destino_id", nullable = false)
    private Jogador jogadorDestino;

    @Column(nullable = false)
    private double valorContratado; // Valor emprestado inicialmente

    @Column(nullable = false)
    private double valorAcordado; // Valor total a ser pago pelo devedor

    @Column(nullable = false)
    private double saldoDevedor; // Saldo restante a pagar

    @Column
    private double taxaJuros;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEmprestimo status;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime dataEmprestimo;

}

