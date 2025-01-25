package com.nossogame.bancoimobiliario.mapper;

import com.nossogame.bancoimobiliario.dto.RankingDto;
import com.nossogame.bancoimobiliario.model.Ranking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RankingMapper {

    RankingMapper INSTANCE = Mappers.getMapper(RankingMapper.class);

    @Mapping(source = "jogador.nome", target = "nomeJogador")
    RankingDto toDto(Ranking ranking);

    List<RankingDto> toDtoList(List<Ranking> rankings);
}
