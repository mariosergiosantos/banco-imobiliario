package com.nossogame.bancoimobiliario.mapper;

import com.nossogame.bancoimobiliario.dto.JogadorDto;
import com.nossogame.bancoimobiliario.model.Jogador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JogadorMapper {

    JogadorMapper INSTANCE = Mappers.getMapper(JogadorMapper.class);

    @Mapping(source = "sala.id", target = "salaId")
    JogadorDto toDTO(Jogador jogador);
}
