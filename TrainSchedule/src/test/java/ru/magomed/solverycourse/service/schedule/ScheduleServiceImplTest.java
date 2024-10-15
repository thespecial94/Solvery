package ru.magomed.solverycourse.service.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.magomed.solverycourse.dto.ScheduleDTO;
import ru.magomed.solverycourse.dto.ScheduleFindTrainDTO;
import ru.magomed.solverycourse.dto.ScheduleTrainListDTO;
import ru.magomed.solverycourse.entity.ScheduleEntity;
import ru.magomed.solverycourse.entity.StationEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import ru.magomed.solverycourse.mapper.ScheduleMapper;
import ru.magomed.solverycourse.mapper.ScheduleTrainMapper;
import ru.magomed.solverycourse.repository.ScheduleRepository;
import ru.magomed.solverycourse.service.station.StationValidationService;
import ru.magomed.solverycourse.service.train.TrainValidationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ScheduleValidationService scheduleValidationService;
    @Mock
    private TrainValidationService trainValidationService;
    @Mock
    private StationValidationService stationValidationService;
    @Mock
    private ScheduleMapper scheduleMapper;
    @Mock
    private ScheduleTrainMapper scheduleTrainMapper;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private List<ScheduleEntity> schedules;
    private ScheduleEntity schedule;
    private TrainEntity train;
    private final int stationId = 1;
    public final static  DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private LocalDateTime arrivalDateTime1;
    private LocalDateTime departureDateTime1;
    private LocalDateTime arrivalDateTime2;
    private LocalDateTime departureDateTime2;
    private ScheduleDTO scheduleDTO;
    private final int trainId = 1;

    @BeforeEach
    void setUp() {
        arrivalDateTime1 = LocalDateTime.parse("11-07-2024 20:15:30", DATE_TIME_FORMATTER);
        departureDateTime1 = LocalDateTime.parse("11-07-2024 22:15:30", DATE_TIME_FORMATTER);
        arrivalDateTime2 = LocalDateTime.parse("12-10-2024 11:15:30", DATE_TIME_FORMATTER);
        departureDateTime2 = LocalDateTime.parse("12-10-2024 13:15:30", DATE_TIME_FORMATTER);
        schedules = new ArrayList<>();
        schedule = new ScheduleEntity();
        ScheduleEntity schedule2 = new ScheduleEntity();

        schedule.setId(1);
        schedule.setArrivalTime(arrivalDateTime1);
        schedule.setDepartureTime(departureDateTime1);

        schedule2.setId(2);
        schedule2.setArrivalTime(arrivalDateTime2);
        schedule2.setDepartureTime(departureDateTime2);

        schedules.add(schedule);
        schedules.add(schedule2);

        train = new TrainEntity();
        train.setId(1);
        train.setCountSeats(50);

        scheduleDTO = new ScheduleDTO();
        scheduleDTO.setTrainId(trainId);
        scheduleDTO.setArrivalTime(arrivalDateTime1);
        scheduleDTO.setDepartureTime(departureDateTime1);
        scheduleDTO.setStationId(stationId);
    }

    @Test
    @DisplayName("Проверка на возвращаемость списка расписаний")
    void shouldReturnListOfSchedulesWhenGettingSchedules() {
        ScheduleTrainListDTO scheduleDTO2 = new ScheduleTrainListDTO();
        scheduleDTO2.setTrain(train);
        scheduleDTO2.setArrivalTime(arrivalDateTime1);
        scheduleDTO2.setDepartureTime(departureDateTime1);
        ScheduleTrainListDTO scheduleDTO1 = new ScheduleTrainListDTO();
        scheduleDTO2.setTrain(train);
        scheduleDTO2.setArrivalTime(arrivalDateTime2);
        scheduleDTO2.setDepartureTime(departureDateTime2);
        List<ScheduleTrainListDTO> schedulesDTO = List.of(scheduleDTO1,scheduleDTO2);

        when(scheduleRepository.getByStationId(stationId)).thenReturn(schedules);
        when(scheduleTrainMapper.toDto(schedules.get(0))).thenReturn(scheduleDTO1);
        when(scheduleTrainMapper.toDto(schedules.get(1))).thenReturn(scheduleDTO2);

        List<ScheduleTrainListDTO> schedulesResult = scheduleService.getByStationId(stationId);

        assertEquals(2, schedulesResult.size());
        assertEquals(schedulesDTO.get(0), schedulesResult.get(0));
        assertEquals(schedulesDTO.get(1), schedulesResult.get(1));
        verify(scheduleRepository, times(1)).getByStationId(stationId);
        verify(scheduleTrainMapper, times(1)).toDto(schedules.get(0));
        verify(scheduleTrainMapper, times(1)).toDto(schedules.get(1));
        verify(scheduleValidationService, times(1)).validateSchedulesEmpty(schedules);
    }

    @Test
    @DisplayName("Проверка на корректный Id, когда добавлено расписание ")
    void shouldReturnScheduleIdWhenAddSchedule() {
        StationEntity station = new StationEntity();
        station.setId(1);
        station.setName("Arctic");
        schedule.setStationEntity(station);
        schedule.setTrainEntity(train);

        when(scheduleMapper.toEntity(any(ScheduleDTO.class))).thenReturn(schedule);
        when(scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(schedule);
        int scheduleId = scheduleService.add(scheduleDTO);

        assertEquals(schedule.getId(), scheduleId);
        verify(scheduleMapper, times(1)).toEntity(scheduleDTO);
        verify(scheduleRepository, times(1)).save(schedule);
        verify(trainValidationService, times(1)).validateEntityById(trainId);
        verify(stationValidationService, times(1)).validateEntityById(stationId);
    }

    @Test
    @DisplayName("Проверка поиск поезда между интервалом дат и времени от станции одной до другой станции")
    void getTrainByIntervalTime() {
        ScheduleFindTrainDTO scheduleFindTrainDTO  = new ScheduleFindTrainDTO();
        scheduleFindTrainDTO.setArrivalTime(arrivalDateTime1);
        scheduleFindTrainDTO.setDepartureTime(departureDateTime1);
        scheduleFindTrainDTO.setArrivalStationId(1);
        scheduleFindTrainDTO.setDepartureStationId(2);

        when(scheduleValidationService.validateTrainEmpty(
                scheduleFindTrainDTO.getArrivalStationId(),
                scheduleFindTrainDTO.getDepartureStationId(), scheduleFindTrainDTO.getArrivalTime(),
                scheduleFindTrainDTO.getDepartureTime())).thenReturn(train);

        int trainResultId = scheduleService.getTrainByIntervalTime(scheduleFindTrainDTO);

        assertEquals(train.getId(), trainResultId);

        verify(stationValidationService, times(1)).
                validateEntityById(scheduleFindTrainDTO.getArrivalStationId());
        verify(stationValidationService, times(1)).
                validateEntityById(scheduleFindTrainDTO.getDepartureStationId());
        verify(scheduleValidationService, times(1)).
                validateTrainEmpty(scheduleFindTrainDTO.getArrivalStationId(),
                scheduleFindTrainDTO.getDepartureStationId(), scheduleFindTrainDTO.getArrivalTime(),
                scheduleFindTrainDTO.getDepartureTime());
    }
}