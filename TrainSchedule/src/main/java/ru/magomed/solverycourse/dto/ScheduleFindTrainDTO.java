package ru.magomed.solverycourse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleFindTrainDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    @NotNull(message = "Номер станции отправления не должно быть пустым.")
    private int arrivalStationId;
    @NotNull(message = "Номер станции прибытия не должно быть пустым.")
    private int departureStationId;
    @NotNull(message = "Дата и время отправления поезда не должно быть пустым.")
    private LocalDateTime arrivalTime;
    @NotNull(message = "Дата и время прибытия поезда не должно быть пустым.")
    private LocalDateTime departureTime;
}
