package ru.magomed.solverycourse.service.passenger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.magomed.solverycourse.dto.TicketDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.mapper.PassengerMapper;
import ru.magomed.solverycourse.repository.PassengerRepository;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @Mock
    private PassengerValidationService passengerValidationService;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    private PassengerEntity passengerEntity;
    private TicketDTO ticketDTO;

    @BeforeEach
    void setUp() {
        passengerEntity = new PassengerEntity();
        passengerEntity.setId(1);
        passengerEntity.setFirstName("John");
        passengerEntity.setLastName("Doe");
        passengerEntity.setBirthday(Date.valueOf("2000-01-01"));

        ticketDTO  = new TicketDTO();
        ticketDTO.setId(1);
        ticketDTO.setTrainId(1);
        ticketDTO.setBirthday(Date.valueOf("2000-01-01"));
        ticketDTO.setFirstName("John");
        ticketDTO.setLastName("Doe");

    }

    @Test
    @DisplayName("Добавления пассажира при получение данных из билета")
    void shouldReturnPassengerIdWhenAddPassenger() {
        when(passengerMapper.toEntity(any(TicketDTO.class))).thenReturn(passengerEntity);
        when(passengerRepository.save(any(PassengerEntity.class))).thenReturn(passengerEntity);

        int passengerId = passengerService.add(ticketDTO).getId();

        assertEquals(passengerId, passengerEntity.getId());
        verify(passengerMapper, times(1)).toEntity(ticketDTO);
        verify(passengerRepository, times(1)).save(passengerEntity);
        verify(passengerValidationService, times(1)).validateByFirstNameAndLastNameAndBirthday(
                ticketDTO.getFirstName(), ticketDTO.getLastName(), ticketDTO.getBirthday());
    }

}