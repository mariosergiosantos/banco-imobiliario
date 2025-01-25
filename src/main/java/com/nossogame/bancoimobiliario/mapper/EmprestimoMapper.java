package com.nossogame.bancoimobiliario.mapper;

import com.nossogame.bancoimobiliario.dto.EmprestimoDto;
import com.nossogame.bancoimobiliario.model.Emprestimo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmprestimoMapper {

    EmprestimoMapper INSTANCE = Mappers.getMapper(EmprestimoMapper.class);

    @Mapping(source = "jogadorOrigem.id", target = "jogadorOrigemId")
    @Mapping(source = "jogadorDestino.id", target = "jogadorDestinoId")
    EmprestimoDto toDTO(Emprestimo emprestimo);

}
