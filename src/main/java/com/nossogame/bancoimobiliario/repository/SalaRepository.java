package com.nossogame.bancoimobiliario.repository;

import com.nossogame.bancoimobiliario.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, String> {
}
