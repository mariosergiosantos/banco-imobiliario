package com.nossogame.bancoimobiliario.mapper;

import com.nossogame.bancoimobiliario.dto.SalaDto;
import com.nossogame.bancoimobiliario.model.Sala;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {JogadorMapper.class, PropriedadeMapper.class})
public interface SalaMapper {

    SalaMapper INSTANCE = Mappers.getMapper(SalaMapper.class);

    SalaDto toDTO(Sala sala);

    List<SalaDto> toDTOs(List<Sala> salas);
}
