package ru.magomed.solverycourse.mapper;

import org.mapstruct.Mapper;
import ru.magomed.solverycourse.dto.TicketDTO;
import ru.magomed.solverycourse.entity.TicketEntity;

@Mapper(componentModel = "spring")
public interface TicketMapper extends DtoToEntityMapper<TicketDTO, TicketEntity> {
}
