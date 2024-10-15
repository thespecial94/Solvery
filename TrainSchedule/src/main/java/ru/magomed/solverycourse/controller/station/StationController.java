package ru.magomed.solverycourse.controller.station;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magomed.solverycourse.dto.StationDTO;
import ru.magomed.solverycourse.service.station.StationServiceImpl;

import java.util.Map;

@Tag(name = "Станции")
@RestController
@RequestMapping("/employee/stations")
public class StationController {
    private final StationServiceImpl stationService;

    @Autowired
    public StationController(StationServiceImpl stationService) {
        this.stationService = stationService;
    }

    @Operation(summary = "Добавление новой станции",
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
    public ResponseEntity<Integer> add(@Valid @RequestBody StationDTO stationDTO) {
        return ResponseEntity.ok(stationService.add(stationDTO));
    }
}
