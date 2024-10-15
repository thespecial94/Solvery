package ru.magomed.solverycourse.service.ticket;

import ru.magomed.solverycourse.dto.TicketDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TicketEntity;
import ru.magomed.solverycourse.entity.TrainEntity;

public interface TicketService {
    String buy(TicketDTO ticketDTO);
    TicketEntity add(TrainEntity train, PassengerEntity passenger);
}
