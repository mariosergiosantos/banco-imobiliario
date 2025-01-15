package com.nossogame.bancoimobiliario.mapper;

import com.nossogame.bancoimobiliario.dto.JogadorDto;
import com.nossogame.bancoimobiliario.dto.VencedorJogoDto;
import com.nossogame.bancoimobiliario.model.Jogador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface JogadorMapper {

    JogadorMapper INSTANCE = Mappers.getMapper(JogadorMapper.class);

    @Mapping(source = "sala.id", target = "salaId")
    JogadorDto toDTO(Jogador jogador);

    List<JogadorDto> toDTOs(List<Jogador> jogadores);

    @Mapping(source = "sala.id", target = "salaId")
    VencedorJogoDto toDto(Jogador jogador);
}
