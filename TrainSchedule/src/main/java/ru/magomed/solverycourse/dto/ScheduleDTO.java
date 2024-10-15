package ru.magomed.solverycourse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    @NotNull(message = "Номер поезда не должно быть пустым.")
    private int trainId;
    @NotNull(message = "Номер станции не должно быть пустым.")
    private int stationId;
    @NotNull(message = "Дата и время отправления поезда не должно быть пустым.")
    private LocalDateTime arrivalTime;
    @NotNull(message = "Дата и время прибытия поезда не должно быть пустым.")
    private LocalDateTime departureTime;
}
