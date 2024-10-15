package ru.magomed.solverycourse.controller.ticket;

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
import ru.magomed.solverycourse.dto.TicketDTO;
import ru.magomed.solverycourse.service.ticket.TicketServiceImpl;

import java.util.Map;

@Tag(name = "Билеты")
@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketServiceImpl ticketService;

    @Autowired
    public TicketController(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(summary = "Покупка билета",
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
    @PostMapping("/buy")
    public ResponseEntity<String> buy(@Valid @RequestBody TicketDTO ticketDTO) {
        return ResponseEntity.ok(ticketService.buy(ticketDTO));
    }
}
