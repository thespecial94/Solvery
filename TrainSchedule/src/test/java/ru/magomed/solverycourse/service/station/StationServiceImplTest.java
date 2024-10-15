package ru.magomed.solverycourse.service.station;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.magomed.solverycourse.dto.StationDTO;
import ru.magomed.solverycourse.entity.StationEntity;
import ru.magomed.solverycourse.mapper.StationMapper;
import ru.magomed.solverycourse.repository.StationRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StationServiceImplTest {

    @Mock
    private StationRepository stationRepository;
    @Mock
    private StationMapper stationMapper;
    @InjectMocks
    private StationServiceImpl stationService;

    @Test
    @DisplayName("Проверка на корректный Id, когда добавлена станция")
    void shouldReturnStationIdWhenAddStation() {
        StationDTO stationDTO = new StationDTO();
        stationDTO.setId(1);
        stationDTO.setName("Arctic");
        StationEntity stationEntity = new StationEntity();
        stationEntity.setId(1);
        stationEntity.setName("Arctic");

        when(stationMapper.toEntity(any(StationDTO.class))).thenReturn(stationEntity);
        when(stationRepository.save(any(StationEntity.class))).thenReturn(stationEntity);
        int expectedId = stationService.add(stationDTO);

        assertEquals(expectedId, stationDTO.getId());
        verify(stationRepository, times(1)).save(stationEntity);
        verify(stationMapper, times(1)).toEntity(any(StationDTO.class));
    }
}