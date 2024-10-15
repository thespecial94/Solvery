package ru.magomed.solverycourse.service.ticket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.magomed.solverycourse.dto.TicketDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TicketEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import ru.magomed.solverycourse.repository.TicketRepository;
import ru.magomed.solverycourse.service.passenger.PassengerServiceImpl;
import ru.magomed.solverycourse.service.schedule.ScheduleValidationService;
import ru.magomed.solverycourse.service.train.TrainValidationService;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final PassengerServiceImpl passengerService;
    private final TrainValidationService trainValidationService;
    private final ScheduleValidationService scheduleValidationService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, PassengerServiceImpl passengerService,
                             TrainValidationService trainValidationService,
                             ScheduleValidationService scheduleValidationService) {
        this.ticketRepository = ticketRepository;
        this.passengerService = passengerService;
        this.trainValidationService = trainValidationService;
        this.scheduleValidationService = scheduleValidationService;
    }

    @Override
    public String buy(TicketDTO ticketDTO) {
        log.info("Процесс по покупки билета запущен!");
        log.info("Проверка на то, что поезд существует!");
        TrainEntity train = trainValidationService.validateEntityById(ticketDTO.getTrainId());

        log.info("Проверка на то, что поезд отправления не менее" + ScheduleValidationService.MINUTES + " минут!");
        scheduleValidationService.validateArrivalDateTimeByMinutes(ticketDTO.getTrainId());

        log.info("Проверка на то, что в поезде есть свободное место");
        trainValidationService.validateTrainCountSeats(train);

        log.info("Добавление пассажира, если его нет в БД");
        PassengerEntity passenger = passengerService.add(ticketDTO);
        add(train, passenger);

        return "Билет успешно куплен!";
    }

    @Override
    public TicketEntity add(TrainEntity train, PassengerEntity passenger) {
        TicketEntity ticket = new TicketEntity();
        ticket.setTrainEntity(train);
        ticket.setPassengerEntity(passenger);
        return ticketRepository.save(ticket);
    }
}
