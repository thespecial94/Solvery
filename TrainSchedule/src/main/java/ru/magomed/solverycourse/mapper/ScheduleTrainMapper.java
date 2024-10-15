package ru.magomed.solverycourse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.magomed.solverycourse.dto.ScheduleTrainListDTO;
import ru.magomed.solverycourse.entity.ScheduleEntity;

@Mapper(componentModel = "spring")
public interface ScheduleTrainMapper extends DtoToEntityMapper<ScheduleTrainListDTO, ScheduleEntity> {
    @Mapping(target = "train", source = "trainEntity")
    ScheduleTrainListDTO toDto(ScheduleEntity schedule);
}
