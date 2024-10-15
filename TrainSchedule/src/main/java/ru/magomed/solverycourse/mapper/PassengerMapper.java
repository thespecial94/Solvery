package ru.magomed.solverycourse.mapper;

import org.mapstruct.Mapper;
import ru.magomed.solverycourse.dto.TicketDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;

@Mapper(componentModel = "spring")
public interface PassengerMapper extends DtoToEntityMapper<TicketDTO, PassengerEntity> {
}
