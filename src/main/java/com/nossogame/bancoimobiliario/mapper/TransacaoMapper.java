package com.nossogame.bancoimobiliario.mapper;

import com.nossogame.bancoimobiliario.dto.TransacaoDto;
import com.nossogame.bancoimobiliario.model.Transacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {JogadorMapper.class, PropriedadeMapper.class})
public interface TransacaoMapper {

    TransacaoMapper INSTANCE = Mappers.getMapper(TransacaoMapper.class);

    @Mapping(source = "sala.id", target = "salaId")
    @Mapping(source = "comprador", target = "origem")
    @Mapping(source = "vendedor", target = "destino")
    TransacaoDto toDTO(Transacao transacao);

    List<TransacaoDto> toDTO(List<Transacao> transacoes);
}
