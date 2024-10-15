package ru.magomed.solverycourse.mapper;

import org.mapstruct.Mapper;
import ru.magomed.solverycourse.dto.ScheduleDTO;
import ru.magomed.solverycourse.entity.ScheduleEntity;

@Mapper(componentModel = "spring")
public interface ScheduleMapper extends DtoToEntityMapper<ScheduleDTO, ScheduleEntity> {
}
