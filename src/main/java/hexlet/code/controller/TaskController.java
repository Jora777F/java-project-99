package hexlet.code.controller;

import hexlet.code.dto.task.TaskFilterParams;
import hexlet.code.dto.task.TaskRequestDto;
import hexlet.code.dto.task.TaskResponseDto;
import hexlet.code.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Получение задачи по идентификатору",
            description = "Позволяет получить задачу по идентификатору",
            responses = {
                @ApiResponse(responseCode = "200", description = "Задача успешно получена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskResponseDto.class))),
                @ApiResponse(responseCode = "404", description = "Задача не найдена",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class)))
            })
    @GetMapping("/{id}")
    public TaskResponseDto findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @Operation(summary = "Получение списка задач",
            description = "Позволяет получить список задач",
            responses = {
                @ApiResponse(responseCode = "200", description = "Список задач успешно получен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(arraySchema =
                                    @Schema(implementation = TaskResponseDto.class)))
                )})
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findAll(TaskFilterParams taskFilterParams) {
        List<TaskResponseDto> tasks = taskService.findAll(taskFilterParams);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(tasks.size()));

        return new ResponseEntity<>(tasks, headers, HttpStatus.OK);
    }

    @Operation(summary = "Создание новой задачи", description = "Позволяет создать новую задачу",
            responses = {@ApiResponse(responseCode = "201", description = "Задача успешно создана",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskResponseDto.class)))
            })
    @PostMapping
    public ResponseEntity<TaskResponseDto> save(@RequestBody TaskRequestDto taskRequest) {
        TaskResponseDto taskResponseDto = taskService.save(taskRequest);
        return new ResponseEntity<>(taskResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновление задачи", description = "Позволяет обновить "
            + "только те данные, которые хотим изменить",
            responses = {@ApiResponse(responseCode = "200", description = "Информация о задаче "
                    + "успешно обновлена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskResponseDto.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateById(@PathVariable Long id,
                                                      @RequestBody TaskRequestDto taskRequest) {
        TaskResponseDto taskResponseDto = taskService.updateById(id, taskRequest);
        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Удаление задачи", description = "Позволяет удалить задачу",
            responses = {@ApiResponse(responseCode = "204", description = "Задача успешно удалена",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
