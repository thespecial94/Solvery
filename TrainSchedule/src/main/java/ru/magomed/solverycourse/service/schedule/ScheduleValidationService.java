package ru.magomed.solverycourse.service.schedule;

import org.springframework.stereotype.Service;
import ru.magomed.solverycourse.utils.DateUtils;
import ru.magomed.solverycourse.entity.ScheduleEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import ru.magomed.solverycourse.exception.ScheduleException;
import ru.magomed.solverycourse.exception.TrainException;
import ru.magomed.solverycourse.repository.ScheduleRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleValidationService {
    private final ScheduleRepository scheduleRepository;
    public static final int MINUTES = 10;

    public ScheduleValidationService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void validateArrivalDateTimeByMinutes(int trainId) {
        LocalDateTime curDateTime = LocalDateTime.now();
        LocalDateTime arrivalDateTime = scheduleRepository.findByTrainIdOrderByArrivalTimeDesc(trainId).getArrivalTime();
        Timestamp currentTime = Timestamp.valueOf(curDateTime);
        Timestamp arrivalTime = Timestamp.valueOf(arrivalDateTime);
        if (DateUtils.getMinutesDiffDates(curDateTime,arrivalDateTime) < MINUTES && arrivalTime.after(currentTime)) {
            throw new ScheduleException("До отправления поезда осталось менее " + MINUTES + " минут!");
        }
    }

    public TrainEntity validateTrainEmpty(int arrivalStationId, int departureStationId,
                                          LocalDateTime arrivalTime, LocalDateTime departureTime) {
        return Optional.ofNullable(scheduleRepository.findDistinctByStationEntityInAndArrivalTimeBetween(
                        arrivalStationId, departureStationId, arrivalTime, departureTime))
                .orElseThrow(() -> new TrainException("Поезд не найден в промежутке " + arrivalTime
                        + " - " + departureTime + " от станции " + arrivalStationId + " до станции "
                        + departureStationId + "!"));
    }

    public void validateSchedulesEmpty(List<ScheduleEntity> schedules) {
        if (schedules.isEmpty()) {
            throw new ScheduleException("Расписание поездов отсутствует!");
        }
    }
}
