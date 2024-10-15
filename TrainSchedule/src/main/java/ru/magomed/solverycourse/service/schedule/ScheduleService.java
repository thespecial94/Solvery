package ru.magomed.solverycourse.service.schedule;

import ru.magomed.solverycourse.dto.ScheduleDTO;
import ru.magomed.solverycourse.dto.ScheduleFindTrainDTO;
import ru.magomed.solverycourse.dto.ScheduleTrainListDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleTrainListDTO> getByStationId(int stationId);
    Integer add(ScheduleDTO scheduleDTO);
    Integer getTrainByIntervalTime(ScheduleFindTrainDTO scheduleFindTrainDTO);
}
