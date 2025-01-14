package com.nossogame.bancoimobiliario.repository;

import com.nossogame.bancoimobiliario.model.Casa;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.enuns.CorPropriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade, String> {

    Optional<Propriedade> findByIdAndSalaId(String propriedadeId, String salaId);

    List<Casa> findBySalaIdAndCor(String salaId, CorPropriedade cor);

    List<Propriedade> findByDono(Jogador jogador);
}
