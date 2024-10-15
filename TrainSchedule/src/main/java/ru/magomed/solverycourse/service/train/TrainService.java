package ru.magomed.solverycourse.service.train;

import ru.magomed.solverycourse.dto.TrainDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import java.util.List;

public interface TrainService {
    List<TrainEntity> getTrains();
    Integer add(TrainDTO trainDTO);
    List<PassengerEntity> getPassengersByTrainId(int id);
}
