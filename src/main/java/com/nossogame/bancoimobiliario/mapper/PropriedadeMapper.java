package com.nossogame.bancoimobiliario.mapper;

import com.nossogame.bancoimobiliario.dto.PropriedadeDto;
import com.nossogame.bancoimobiliario.model.Casa;
import com.nossogame.bancoimobiliario.model.Propriedade;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {JogadorMapper.class})
public interface PropriedadeMapper {

    PropriedadeMapper INSTANCE = Mappers.getMapper(PropriedadeMapper.class);

    @Mapping(target = "dono", source = "dono", qualifiedByName = "toDTOResumido")
    PropriedadeDto toDTO(Propriedade propriedade);

    @AfterMapping
    default PropriedadeDto afterMapper(Propriedade source, @MappingTarget PropriedadeDto target) {

        if (source instanceof Casa) {
            Casa casa = (Casa) source;

            target.setHotel(casa.isHotel());
            target.setCor(casa.getCor().getRgb());
        }

        return target;
    }
}
