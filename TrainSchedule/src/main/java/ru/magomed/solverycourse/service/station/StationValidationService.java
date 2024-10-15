package ru.magomed.solverycourse.service.station;

import org.springframework.stereotype.Service;
import ru.magomed.solverycourse.entity.StationEntity;
import ru.magomed.solverycourse.exception.StationException;
import ru.magomed.solverycourse.repository.StationRepository;

import java.util.Optional;

@Service
public class StationValidationService {
    private final StationRepository stationRepository;

    public StationValidationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public StationEntity validateEntityById(int id) {
        return Optional
                .ofNullable(stationRepository.findById(id))
                .orElseThrow(() -> new StationException("Нет данных по входящему id станции = " + id + "!"));
    }
}
