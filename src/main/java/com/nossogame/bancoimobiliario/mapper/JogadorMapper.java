package com.nossogame.bancoimobiliario.mapper;

import com.nossogame.bancoimobiliario.dto.JogadorDto;
import com.nossogame.bancoimobiliario.dto.VencedorJogoDto;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {SalaMapper.class})
public interface JogadorMapper {

    JogadorMapper INSTANCE = Mappers.getMapper(JogadorMapper.class);

    JogadorDto toDTO(Jogador jogador);

    @Named("toDTOResumido")
    @Mapping(target = "saldo", ignore = true)
    @Mapping(target = "isAdmin", ignore = true)
    JogadorDto toDTOResumido(Jogador jogador);

    List<JogadorDto> toDTOs(List<Jogador> jogadores);

    @Mapping(source = "sala.id", target = "salaId")
    VencedorJogoDto toDto(Jogador jogador);
}
