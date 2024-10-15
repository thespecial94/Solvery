package ru.magomed.solverycourse.service.train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.magomed.solverycourse.dto.TrainDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import ru.magomed.solverycourse.mapper.TrainMapper;
import ru.magomed.solverycourse.repository.TrainRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainServiceImplTest {

    @Mock
    private TrainValidationService trainValidationService;
    @Mock
    private TrainRepository trainRepository;
    @Mock
    private TrainMapper trainMapper;

    @InjectMocks
    private TrainServiceImpl trainService;

    private TrainEntity train1;
    private TrainEntity train2;

    @BeforeEach
    void setUp() {
        train1 = new TrainEntity();
        train2 = new TrainEntity();
        train1.setId(1);
        train2.setId(2);
        train1.setCountSeats(50);
        train2.setCountSeats(70);

    }

    @Test
    @DisplayName("Проверка на возврат списка поездов")
    void shouldReturnListOfTrainsWhenGettingTrains() {
        List<TrainEntity> trains = List.of(train1, train2);

        when(trainRepository.findAllByOrderById()).thenReturn(trains);
        List<TrainEntity> transResult = trainService.getTrains();

        assertEquals(2, transResult.size());
        assertEquals(trains.get(0), transResult.get(0));
        assertEquals(trains.get(1), transResult.get(1));
        verify(trainRepository, times(1)).findAllByOrderById();
        verify(trainValidationService, times(1)).validateCountTrain();
    }

    @Test
    @DisplayName("Проверка на корректный Id, когда добавлен поезд")
    void shouldReturnTrainIdWhenAddTrain() {
        TrainDTO trainDTO = new TrainDTO();
        trainDTO.setId(1);
        trainDTO.setCountSeats(50);

        when(trainMapper.toEntity(any(TrainDTO.class))).thenReturn(train1);
        when(trainRepository.save(any(TrainEntity.class))).thenReturn(train1);

        int expectedId = trainService.add(trainDTO);

        assertEquals(expectedId, trainDTO.getId());
        verify(trainRepository, times(1)).save(train1);
        verify(trainMapper, times(1)).toEntity(any(TrainDTO.class));
    }

    @Test
    @DisplayName("Проверка на корректный возврат пассажиров при поиске по id поезду")
    void shouldReturnPassengersWhenSearchByTrainId() {
        PassengerEntity passenger1 = new PassengerEntity();
        passenger1.setId(1);
        passenger1.setLastName("Oleg");
        passenger1.setFirstName("Ivanov");
        PassengerEntity passenger2 = new PassengerEntity();
        passenger2.setId(2);
        passenger2.setLastName("Alex");
        passenger2.setFirstName("Busharin");
        List<PassengerEntity> passengers = List.of(passenger1, passenger2);

        when(trainRepository.getPassengersByTrainId(train2.getId())).thenReturn(passengers);
        List<PassengerEntity> passengersResult = trainService.getPassengersByTrainId(train2.getId());

        assertEquals(passengersResult.size(), passengers.size());
        assertEquals(passengersResult.get(0), passengers.get(0));
        assertEquals(passengersResult.get(1), passengers.get(1));
        verify(trainRepository, times(1)).getPassengersByTrainId(train2.getId());
        verify(trainValidationService, times(1)).validateEmptyTrain(passengers);
        verify(trainValidationService, times(1)).validateEntityById(train2.getId());
    }
}