package com.nossogame.bancoimobiliario.repository;

import com.nossogame.bancoimobiliario.model.Emprestimo;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, String> {

    /*List<Emprestimo> findByJogadorOrigemId(String jogadorOrigemId);
    List<Emprestimo> findByJogadorDestinoId(String jogadorDestinoId);*/

    boolean existsByRecebedorAndStatus(Jogador recebedor, StatusEmprestimo status);
}
