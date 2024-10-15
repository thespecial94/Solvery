package ru.magomed.solverycourse.service.ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.magomed.solverycourse.dto.TicketDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TicketEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import ru.magomed.solverycourse.repository.TicketRepository;
import ru.magomed.solverycourse.service.passenger.PassengerServiceImpl;
import ru.magomed.solverycourse.service.schedule.ScheduleValidationService;
import ru.magomed.solverycourse.service.train.TrainValidationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TrainValidationService trainValidationService;
    @Mock
    private ScheduleValidationService scheduleValidationService;
    @Mock
    private PassengerServiceImpl passengerService;
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private PassengerEntity passenger;
    private TrainEntity train;
    private TicketEntity ticket;

    @BeforeEach
    void setUp() {
        passenger = new PassengerEntity();
        passenger.setLastName("John");
        passenger.setFirstName("John");
        passenger.setId(1);

        train = new TrainEntity();
        train.setId(1);
        train.setCountSeats(100);

        ticket = new TicketEntity();
        ticket.setId(1);
        ticket.setPassengerEntity(passenger);
        ticket.setTrainEntity(train);
    }

    @Test
    @DisplayName("Проверка на корректное сообщение, когда куплен билет")
    void shouldReturnMessageWhenBuyTicket() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(1);
        ticketDTO.setTrainId(1);
        ticketDTO.setFirstName("John");
        ticketDTO.setLastName("Doe");

        when(passengerService.add(ticketDTO)).thenReturn(passenger);

        ticketService.add(train, passenger);
        String expectedMessage = ticketService.buy(ticketDTO);

        assertEquals(expectedMessage, "Билет успешно куплен!");
        verify(passengerService, times(1)).add(ticketDTO);
        verify(trainValidationService, times(1)).validateEntityById(ticketDTO.getId());
        verify(scheduleValidationService, times(1)).
                validateArrivalDateTimeByMinutes(ticketDTO.getId());
    }

    @Test
    @DisplayName("Проверка на корректный Id, когда добавлен билет")
    void shouldReturnTicketIdWhenAddTicket() {
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticket);

        int expectedId = ticketService.add(train, passenger).getId();

        assertEquals(expectedId, ticket.getId());
    }
}