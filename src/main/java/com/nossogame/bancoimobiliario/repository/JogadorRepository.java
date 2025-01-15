package com.nossogame.bancoimobiliario.repository;

import com.nossogame.bancoimobiliario.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, String> {

    Optional<Jogador> findByIdAndSalaId(String jogadorId, String salaId);

    Optional<List<Jogador>> findBySalaId(String salaId);
}
