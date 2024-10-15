package ru.magomed.solverycourse.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.magomed.solverycourse.entity.ScheduleEntity;
import ru.magomed.solverycourse.entity.TrainEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleEntity,Integer> {
    @Query("select sched,sched.trainEntity,sched.stationEntity from ScheduleEntity sched " +
            "join sched.stationEntity st " +
            "where st.id = :stationId")
    List<ScheduleEntity> getByStationId(int stationId);

    @Query("select distinct sch.trainEntity from ScheduleEntity sch " +
            "join sch.stationEntity st " +
            "where st.id in (:arrivalStationId,:departureStationId)" +
            "and sch.arrivalTime between :arrivalTimeStation and :departureTimeStation")
    TrainEntity findDistinctByStationEntityInAndArrivalTimeBetween(int arrivalStationId, int departureStationId,
                                                                   LocalDateTime arrivalTimeStation,
                                                                   LocalDateTime departureTimeStation);

    @Query("select sched from ScheduleEntity sched " +
            "join sched.trainEntity train " +
            "where train.id = :trainId " +
            "order by sched.arrivalTime desc limit 1")
    ScheduleEntity findByTrainIdOrderByArrivalTimeDesc(int trainId);
}
