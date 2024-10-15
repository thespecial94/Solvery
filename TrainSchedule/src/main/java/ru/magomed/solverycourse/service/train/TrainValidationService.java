package ru.magomed.solverycourse.service.train;

import org.springframework.stereotype.Service;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import ru.magomed.solverycourse.exception.TrainException;
import ru.magomed.solverycourse.repository.TrainRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TrainValidationService {
    private final TrainRepository trainRepository;

    public TrainValidationService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public TrainEntity validateEntityById(int id) {
        return Optional
                .ofNullable(trainRepository.findById(id))
                .orElseThrow(() -> new TrainException("Нет данных по входящему номеру " + id + " поезда!"));
    }

    public void validateTrainCountSeats(TrainEntity trainEntity) {
        if (trainEntity.getCountSeats() + 1 > TrainServiceImpl.COUNT_SEATS) {
            throw new TrainException("Нет мест в поезде " + trainEntity.getId());
        }
    }

    public void validateEmptyTrain(List<PassengerEntity> passengers) {
        if (passengers.isEmpty()) {
            throw new TrainException("Поезд пустой!");
        }
    }

    public void validateCountTrain() {
        if(trainRepository.count() == 0) {
            throw new TrainException("Список поездов пуст!");
        }
    }
}
