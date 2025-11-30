package hexlet.code.controller;

import hexlet.code.dto.task.status.TaskStatusRequest;
import hexlet.code.dto.task.status.TaskStatusResponse;
import hexlet.code.service.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task_statuses")
@RequiredArgsConstructor
@Tag(name = "Статусы задач", description = "Взаимодействие со статусами задач")
@SecurityRequirement(name = "JWT")
public class TaskStatusController {

    private final TaskStatusService statusService;

    @Operation(summary = "Получение статуса по идентификатору",
            description = "Позволяет получить статус по идентификатору",
            responses = {
                @ApiResponse(responseCode = "200", description = "Статус успешно получен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskStatusResponse.class))),
                @ApiResponse(responseCode = "404", description = "Статус не найден",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class))),
            })
    @GetMapping("/{id}")
    public TaskStatusResponse findById(@PathVariable Long id) {
        return statusService.findById(id);
    }

    @Operation(summary = "Получение списка статусов",
            description = "Позволяет получить список статусов",
            responses = {
                @ApiResponse(responseCode = "200", description = "Список статусов успешно получен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(arraySchema =
                                    @Schema(implementation = TaskStatusResponse.class)))
                    )})
    @GetMapping
    public ResponseEntity<List<TaskStatusResponse>> findAll() {
        List<TaskStatusResponse> statuses = statusService.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(statuses.size()));
        return new ResponseEntity<>(statuses, headers, HttpStatus.OK);
    }

    @Operation(summary = "Обновление статуса", description = "Позволяет обновить "
            + "только те данные, которые хотим изменить",
            responses = {@ApiResponse(responseCode = "200", description = "Информация о статусе "
                    + "успешно обновлена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskStatusResponse.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<TaskStatusResponse> updateById(@PathVariable Long id,
                                                         @RequestBody @Valid TaskStatusRequest taskStatusRequest) {
        TaskStatusResponse taskStatusResponse = statusService.updateById(id, taskStatusRequest);
        return new ResponseEntity<>(taskStatusResponse, HttpStatus.OK);
    }

    @Operation(summary = "Создание нового статуса", description = "Позволяет создать новый статус",
            responses = {@ApiResponse(responseCode = "201", description = "Пользователь успешно создан",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskStatusResponse.class)))
            })
    @PostMapping
    public ResponseEntity<TaskStatusResponse> save(@RequestBody @Valid TaskStatusRequest taskStatusRequest) {
        final TaskStatusResponse taskStatusResponse = statusService.save(taskStatusRequest);
        return new ResponseEntity<>(taskStatusResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Удаление статуса", description = "Позволяет удалить статус",
            responses = {@ApiResponse(responseCode = "204", description = "Статус успешно удален",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        statusService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
