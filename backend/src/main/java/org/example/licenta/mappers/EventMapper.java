package org.example.licenta.mappers;

import org.example.licenta.db.entities.EventEntity;
import org.example.licenta.dto.EventDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto toDto(EventEntity eventEntity);

    EventEntity toEntity(EventDto eventDto);
}
