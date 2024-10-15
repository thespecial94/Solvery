package ru.magomed.solverycourse.service.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.magomed.solverycourse.entity.ScheduleEntity;
import ru.magomed.solverycourse.exception.ScheduleException;
import ru.magomed.solverycourse.exception.TrainException;
import ru.magomed.solverycourse.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static ru.magomed.solverycourse.service.schedule.ScheduleServiceImplTest.DATE_TIME_FORMATTER;
import static ru.magomed.solverycourse.service.schedule.ScheduleValidationService.MINUTES;

@ExtendWith(MockitoExtension.class)
class ScheduleValidationServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleValidationService scheduleValidationService;

    private LocalDateTime arrivalDateTime;
    private LocalDateTime departureDateTime;
    private ScheduleEntity schedule;

    @BeforeEach
    void setUp() {
        arrivalDateTime = LocalDateTime.parse("09-10-2024 22:10:00", DATE_TIME_FORMATTER);
        departureDateTime = LocalDateTime.parse("09-10-2024 23:40:30", DATE_TIME_FORMATTER);
        schedule = new ScheduleEntity();
        schedule.setArrivalTime(arrivalDateTime);
        schedule.setDepartureTime(departureDateTime);
    }

    @Test
    void shouldWhenBeforeArrivalTrainNotEnoughTimeExceptionThrow() {
        when(scheduleRepository.findByTrainIdOrderByArrivalTimeDesc(anyInt())).thenReturn(schedule);

        ScheduleException exception = assertThrows(ScheduleException.class,
                () -> scheduleValidationService.validateArrivalDateTimeByMinutes(1));

        assertEquals("До отправления поезда осталось менее " + MINUTES + " минут!", exception.getMessage());
    }

    @Test
    @DisplayName("Выброс исключения, если поезд не был найден в промежутке")
    void shouldWhenTrainEmptyBetweenIntervalDateTimeExceptionThrow() {
        int arrivalStationId = 1;
        int departureStationId = 2;

        TrainException exception = assertThrows(TrainException.class,
                () -> scheduleValidationService.validateTrainEmpty(arrivalStationId, departureStationId,
                        arrivalDateTime,departureDateTime));

        assertEquals("Поезд не найден в промежутке " + arrivalDateTime
                        + " - " + departureDateTime + " от станции " + arrivalStationId + " до станции "
                        + departureStationId + "!", exception.getMessage());
        verify(scheduleRepository, times(1)).findDistinctByStationEntityInAndArrivalTimeBetween(
                arrivalStationId, departureStationId, arrivalDateTime, departureDateTime);
    }

    @Test
    @DisplayName("Выброс исключения, если пустой список расписаний")
    void shouldWhenSchedulesListEmptyExceptionThrow() {
        List<ScheduleEntity> schedules = new ArrayList<>();

        ScheduleException exception = assertThrows(ScheduleException.class,
                () -> scheduleValidationService.validateSchedulesEmpty(schedules));

        assertEquals("Расписание поездов отсутствует!", exception.getMessage());
    }
}