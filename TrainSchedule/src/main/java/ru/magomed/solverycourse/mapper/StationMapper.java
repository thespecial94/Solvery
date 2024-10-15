package ru.magomed.solverycourse.mapper;

import org.mapstruct.Mapper;
import ru.magomed.solverycourse.dto.StationDTO;
import ru.magomed.solverycourse.entity.StationEntity;

@Mapper(componentModel = "spring")
public interface StationMapper extends DtoToEntityMapper<StationDTO, StationEntity> {
}
