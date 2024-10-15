package ru.magomed.solverycourse.controller.schedule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import ru.magomed.solverycourse.dto.ScheduleDTO;
import ru.magomed.solverycourse.dto.ScheduleFindTrainDTO;
import ru.magomed.solverycourse.dto.ScheduleTrainListDTO;
import ru.magomed.solverycourse.service.schedule.ScheduleServiceImpl;

import java.util.List;
import java.util.Map;

@Tag(name = "Расписания")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleServiceImpl scheduleService;

    @Autowired
    public ScheduleController(ScheduleServiceImpl scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Operation(summary = "Получение расписания поездов",
            description = "На вход необходимо передать id станции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены успешно!!!",
                    content = {@Content(mediaType = MimeTypeUtils.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный ввод данных со стороны клиента, данные не получены",
                    content = {@Content(mediaType = MimeTypeUtils.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "500", description = "На стороне сервера произошла ошибка, данные не получены",
                    content = {@Content(mediaType = MimeTypeUtils.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class))})
    })
    @GetMapping("/{stationId}")
    public ResponseEntity<List<ScheduleTrainListDTO>> getSchedulesByStationId(@PathVariable("stationId") int stationId) {
        return ResponseEntity.ok(scheduleService.getByStationId(stationId));
    }
    @Operation(summary = "Добавление расписания поездов",
            description = "На вход необходимо передать тело запроса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены успешно!!!",
                    content = {@Content(mediaType = MimeTypeUtils.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный ввод данных со стороны клиента, данные не получены",
                    content = {@Content(mediaType = MimeTypeUtils.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "500", description = "На стороне сервера произошла ошибка, данные не получены",
                    content = {@Content(mediaType = MimeTypeUtils.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class))})
    })
    @PostMapping
    public ResponseEntity<Integer> add(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.add(scheduleDTO));
    }
    @Operation(summary = "Получение номера поезда",
            description = "На вход необходимо передать id отправления и прибытия станции, промежуток времени и даты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены успешно!!!",
                    content = {@Content(mediaType = MimeTypeUtils.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный ввод данных со стороны клиента, данные не получены",
                    content = {@Content(mediaType = MimeTypeUtils.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "500", description = "На стороне сервера произошла ошибка, данные не получены",
                    content = {@Content(mediaType = MimeTypeUtils.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class))})
    })
    @PostMapping("/train")
    public ResponseEntity<Integer> getTrainByIntervalTime(@Valid @RequestBody ScheduleFindTrainDTO scheduleFindTrainDTO) {
        return ResponseEntity.ok(scheduleService.getTrainByIntervalTime(scheduleFindTrainDTO));
    }
}
