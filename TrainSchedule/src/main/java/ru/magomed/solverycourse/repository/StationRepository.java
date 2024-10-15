package ru.magomed.solverycourse.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.magomed.solverycourse.entity.StationEntity;

@Repository
public interface StationRepository extends CrudRepository<StationEntity,Integer> {
    StationEntity findById(int id);
}
