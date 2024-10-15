package ru.magomed.solverycourse.service.train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import ru.magomed.solverycourse.exception.TrainException;
import ru.magomed.solverycourse.repository.TrainRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TrainValidationServiceTest {

    @Mock
    private TrainRepository trainRepository;


    @InjectMocks
    private TrainValidationService trainValidationService;

    private TrainEntity train;
    @BeforeEach
    void setUp() {
        train = new TrainEntity();
        train.setId(1);
        train.setCountSeats(80);
    }

    @Test
    @DisplayName("Выброс исключения, если не нашелся поезд")
    void shouldWhenNotNumberTrainExceptionThrow() {
        TrainException exception = assertThrows(TrainException.class,
                () -> trainValidationService.validateEntityById(train.getId()));

        assertEquals("Нет данных по входящему номеру " + train.getId() + " поезда!", exception.getMessage());
    }

    @Test
    @DisplayName("Выброс исключения, если нет мест в поезде")
    void shouldWhenNotSeatsInTrainExceptionThrow() {
        TrainException exception = assertThrows(TrainException.class,
                () -> trainValidationService.validateTrainCountSeats(train));

        assertEquals("Нет мест в поезде " + train.getId(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Выброс исключения, если нет пассажиров в поезде")
    void shouldWhenNotPassengersInTrainExceptionThrow() {
        List<PassengerEntity> passengers = new ArrayList<>();

        TrainException exception = assertThrows(TrainException.class,
                () -> trainValidationService.validateEmptyTrain(passengers));

        assertEquals("Поезд пустой!", exception.getMessage());
    }

    @Test
    @DisplayName("Выброс исключения, если нет поездов в БД")
    void shouldWhenNotTrainsExceptionThrow() {
        TrainException exception = assertThrows(TrainException.class,
                () -> trainValidationService.validateCountTrain());

        assertEquals("Список поездов пуст!", exception.getMessage());
    }
}