package ru.magomed.solverycourse.service.train;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.magomed.solverycourse.dto.TrainDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import ru.magomed.solverycourse.mapper.TrainMapper;
import ru.magomed.solverycourse.repository.TrainRepository;
import java.util.List;

@Slf4j
@Service
public class TrainServiceImpl implements TrainService{
    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;
    private final TrainValidationService trainValidationService;
    public static final int COUNT_SEATS = 80;

    @Autowired
    public TrainServiceImpl(TrainRepository trainRepository, TrainMapper trainMapper, TrainValidationService trainValidationService) {
        this.trainRepository = trainRepository;
        this.trainMapper = trainMapper;
        this.trainValidationService = trainValidationService;
    }

    @Override
    public List<TrainEntity> getTrains() {
        log.info("Процесс по получению списка всех поездов запущен!");
        log.info("Проверка на то, что хоть один поезд есть в БД");
        trainValidationService.validateCountTrain();

        return trainRepository.findAllByOrderById();
    }

    @Override
    public Integer add(TrainDTO trainDTO) {
        log.info("Процесс по добавлению поезда запущен!");
        return trainRepository.save(trainMapper.toEntity(trainDTO)).getId();
    }

    @Override
    public List<PassengerEntity> getPassengersByTrainId(int trainId) {
        log.info("Процесс по получению списка пассажиров запущен!");
        log.info("Проверка на то, что поезд сущесвует");
        trainValidationService.validateEntityById(trainId);

        log.info("Получение списка пассажиров");
        List<PassengerEntity> passengers = trainRepository.getPassengersByTrainId(trainId);

        log.info("Проверка на то, что список пассажиров не пустой по текущему поезду");
        trainValidationService.validateEmptyTrain(passengers);
        return passengers;
    }
}
