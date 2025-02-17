package com.nossogame.bancoimobiliario.repository;

import com.nossogame.bancoimobiliario.config.Metric;
import com.nossogame.bancoimobiliario.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, String> {

    @Metric(name = "findBySalaId")
    Optional<List<Transacao>> findBySalaId(String salaId);
}
