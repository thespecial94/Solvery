package ru.magomed.solverycourse.service.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final TrainValidationService trainValidationService;
    private final StationValidationService stationValidationService;
    private final ScheduleValidationService scheduleValidationService;
    private final ScheduleTrainMapper scheduleTrainMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper,
                               TrainValidationService trainValidationService,
                               StationValidationService stationValidationService,
                               ScheduleValidationService scheduleValidationService,
                               ScheduleTrainMapper scheduleTrainMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.trainValidationService = trainValidationService;
        this.stationValidationService = stationValidationService;
        this.scheduleValidationService = scheduleValidationService;
        this.scheduleTrainMapper = scheduleTrainMapper;
    }

    @Override
    public List<ScheduleTrainListDTO> getByStationId(int stationId) {
        List<ScheduleTrainListDTO> schedulesDTO = new ArrayList<>();
        List<ScheduleEntity> schedules = scheduleRepository.getByStationId(stationId);
        scheduleValidationService.validateSchedulesEmpty(schedules);
        for (ScheduleEntity schedule : schedules) {
            schedulesDTO.add(scheduleTrainMapper.toDto(schedule));
        }
        return schedulesDTO;
    }

    @Override
    public Integer add(ScheduleDTO scheduleDTO) {
        log.info("Процесс добавления расписания запущен");
        log.info("Проверка на то, что поезд существует");
        TrainEntity trainEntity = trainValidationService.validateEntityById(scheduleDTO.getTrainId());

        log.info("Проверка на то, что станция существует");
        StationEntity stationEntity = stationValidationService.validateEntityById(scheduleDTO.getStationId());

        log.info("Добавления расписания");
        ScheduleEntity scheduleEntity = scheduleMapper.toEntity(scheduleDTO);
        scheduleEntity.setTrainEntity(trainEntity);
        scheduleEntity.setStationEntity(stationEntity);
        return scheduleRepository.save(scheduleEntity).getId();
    }

    public Integer getTrainByIntervalTime(ScheduleFindTrainDTO scheduleFindTrainDTO) {
        log.info("Процесс по поиску поезда, проходящего от станции A до станции B в заданный промежуток времени запущен");
        log.info("Проверка на то, что станция отправления существует");
        stationValidationService.validateEntityById(scheduleFindTrainDTO.getArrivalStationId());

        log.info("Проверка на то, что станция прибытия существует");
        stationValidationService.validateEntityById(scheduleFindTrainDTO.getDepartureStationId());

        log.info("Получение поезда по промежутки времени, станциям отправления и прибытия");
        return scheduleValidationService.validateTrainEmpty(scheduleFindTrainDTO.getArrivalStationId(),
                scheduleFindTrainDTO.getDepartureStationId(), scheduleFindTrainDTO.getArrivalTime(),
                scheduleFindTrainDTO.getDepartureTime()).getId();
    }
}
