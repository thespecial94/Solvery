package ru.magomed.solverycourse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TicketDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    @NotNull(message = "Номер поезда при покупке билета не должно быть пустым!")
    private int trainId;
    @NotBlank(message = "Имя при покупке билета не должно быть пустым!")
    private String firstName;
    @NotBlank(message = "Фамилия при покупке билета не должно быть пустым!")
    private String lastName;
    @NotNull(message = "Дата рождения при покупке билета не должно быть пустым!")
    private Date birthday;
}
