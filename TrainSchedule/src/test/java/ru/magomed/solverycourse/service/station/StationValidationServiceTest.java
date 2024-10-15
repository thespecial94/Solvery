package ru.magomed.solverycourse.service.station;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.magomed.solverycourse.exception.StationException;
import ru.magomed.solverycourse.repository.StationRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StationValidationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationValidationService stationValidationService;

    @Test
    @DisplayName("Выброс исключения, если не нашелся номер станции")
    void shouldWhenNotNumberStationExceptionThrow() {
        int stationId = 0;

        StationException exception = assertThrows(StationException.class,
                () -> stationValidationService.validateEntityById(stationId));

        assertEquals("Нет данных по входящему id станции = " + stationId + "!", exception.getMessage());
        verify(stationRepository, times(1)).findById(stationId);
    }
}