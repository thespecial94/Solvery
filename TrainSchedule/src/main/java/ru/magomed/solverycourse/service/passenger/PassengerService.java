package ru.magomed.solverycourse.service.passenger;

import ru.magomed.solverycourse.dto.TicketDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;

public interface PassengerService {
    PassengerEntity add(TicketDTO ticketDTO);
}
