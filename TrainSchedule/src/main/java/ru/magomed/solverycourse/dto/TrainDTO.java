package ru.magomed.solverycourse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    @NotNull(message = "Количество мест в поезде не должно быть пустым.")
    private int countSeats;
}
