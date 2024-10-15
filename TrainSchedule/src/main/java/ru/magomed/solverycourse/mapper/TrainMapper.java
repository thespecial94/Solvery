package ru.magomed.solverycourse.mapper;

import org.mapstruct.Mapper;
import ru.magomed.solverycourse.dto.TrainDTO;
import ru.magomed.solverycourse.entity.TrainEntity;

@Mapper(componentModel = "spring")
public interface TrainMapper extends DtoToEntityMapper<TrainDTO, TrainEntity> {
}
