package ru.magomed.solverycourse.controller.train;

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
import ru.magomed.solverycourse.dto.TrainDTO;
import ru.magomed.solverycourse.entity.PassengerEntity;
import ru.magomed.solverycourse.entity.TrainEntity;
import ru.magomed.solverycourse.service.train.TrainServiceImpl;

import java.util.List;
import java.util.Map;

@Tag(name = "Поезда")
@RestController
@RequestMapping("/employee/trains")
public class TrainController {
    private final TrainServiceImpl trainService;

    @Autowired
    public TrainController(TrainServiceImpl trainService) {
        this.trainService = trainService;
    }

    @Operation(summary = "Получение списка поездов",
            description = "На вход ничего не надо подавать")
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
    @GetMapping
    public ResponseEntity<List<TrainEntity>> getTrains() {
        return ResponseEntity.ok(trainService.getTrains());
    }

    @Operation(summary = "Добавление нового поезда",
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
    public ResponseEntity<Integer> add(@Valid @RequestBody TrainDTO trainDTO) {
        return ResponseEntity.ok(trainService.add(trainDTO));
    }

    @Operation(summary = "Получение списка зарегистрированных пассажиров",
            description = "На вход необходимо передать id поезда")
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
    @GetMapping("/{trainId}/passengers")
    public ResponseEntity<List<PassengerEntity>> getPassengersByTrain(@PathVariable("trainId") int trainId) {
        return ResponseEntity.ok(trainService.getPassengersByTrainId(trainId));
    }
}
