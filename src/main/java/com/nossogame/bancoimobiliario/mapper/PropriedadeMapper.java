package com.nossogame.bancoimobiliario.mapper;

import com.nossogame.bancoimobiliario.dto.PropriedadeDto;
import com.nossogame.bancoimobiliario.model.Casa;
import com.nossogame.bancoimobiliario.model.Propriedade;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PropriedadeMapper {

    PropriedadeMapper INSTANCE = Mappers.getMapper(PropriedadeMapper.class);

    @Mapping(source = "sala.id", target = "salaId")
    @Mapping(source = "dono.id", target = "donoId")
    PropriedadeDto toDTO(Propriedade propriedade);

    @AfterMapping
    default PropriedadeDto afterMapper(Propriedade source, @MappingTarget PropriedadeDto target) {

        if (source instanceof Casa) {
            Casa casa = (Casa) source;

            target.setNumeroCasas(casa.getNumeroCasas());
            target.setHotel(casa.isHotel());
            target.setCor(casa.getCor().name());
        }

        return target;
    }
}
