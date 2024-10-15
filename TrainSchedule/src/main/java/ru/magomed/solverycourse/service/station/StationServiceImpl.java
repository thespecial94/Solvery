package ru.magomed.solverycourse.service.station;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.magomed.solverycourse.dto.StationDTO;
import ru.magomed.solverycourse.mapper.StationMapper;
import ru.magomed.solverycourse.repository.StationRepository;

@Slf4j
@Service
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }

    @Override
    public int add(StationDTO stationDTO) {
        log.info("Процесс добавления станции запущен");
        return stationRepository.save(stationMapper.toEntity(stationDTO)).getId();
    }
}
