package ru.magomed.solverycourse.service.passenger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.magomed.solverycourse.dto.TicketDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.mapper.PassengerMapper;
import ru.magomed.solverycourse.repository.PassengerRepository;

@Slf4j
@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PassengerValidationService passengerValidationService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, PassengerMapper passengerMapper, PassengerValidationService passengerValidationService) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
        this.passengerValidationService = passengerValidationService;
    }

    @Override
    public PassengerEntity add(TicketDTO ticketDTO) {
        log.info("Проверка по условиям на то, что клиент уже существует в БД");
        passengerValidationService.validateByFirstNameAndLastNameAndBirthday(ticketDTO.getFirstName(),
                ticketDTO.getLastName(), ticketDTO.getBirthday());

        return passengerRepository.save(passengerMapper.toEntity(ticketDTO));
    }
}
