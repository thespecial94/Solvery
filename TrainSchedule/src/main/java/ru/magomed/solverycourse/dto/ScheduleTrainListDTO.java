package ru.magomed.solverycourse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.magomed.solverycourse.entity.TrainEntity;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleTrainListDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    private TrainEntity train;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
}
