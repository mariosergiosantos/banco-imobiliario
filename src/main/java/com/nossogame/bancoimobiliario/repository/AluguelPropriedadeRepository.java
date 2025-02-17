package com.nossogame.bancoimobiliario.repository;

import com.nossogame.bancoimobiliario.model.AluguelPropriedade;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.model.enuns.TipoAluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AluguelPropriedadeRepository extends JpaRepository<AluguelPropriedade, String> {
    Optional<AluguelPropriedade> findByPropriedadeAndTipoAluguel(Propriedade propriedade, TipoAluguel tipoAluguel);
}
