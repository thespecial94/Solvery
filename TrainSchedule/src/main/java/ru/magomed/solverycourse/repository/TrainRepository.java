package ru.magomed.solverycourse.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TrainEntity;

import java.util.List;

@Repository
public interface TrainRepository extends CrudRepository<TrainEntity,Integer> {
    List<TrainEntity> findAllByOrderById();
    TrainEntity findById(int id);
    @Query("select train.passengerEntities from TrainEntity train " +
            "where train.id = :id")
    List<PassengerEntity> getPassengersByTrainId(int id);
}
