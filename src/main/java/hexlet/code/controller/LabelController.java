package hexlet.code.controller;

import hexlet.code.dto.label.LabelRequestDto;
import hexlet.code.dto.label.LabelResponseDto;
import hexlet.code.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/labels")
@RequiredArgsConstructor
@Tag(name = "Метки", description = "Взаимодействие с метками")
@SecurityRequirement(name = "JWT")
public class LabelController {

    private final LabelService labelService;

    @Operation(summary = "Получение метки по идентификатору",
            description = "Позволяет получить метку по идентификатору",
            responses = {
                @ApiResponse(responseCode = "200", description = "Метка успешно получена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LabelResponseDto.class))),
                @ApiResponse(responseCode = "404", description = "Метка не найдена",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class))),
            })
    @GetMapping("/{id}")
    public LabelResponseDto findById(@PathVariable Long id) {
        return labelService.findById(id);
    }

    @Operation(summary = "Получение списка меток",
            description = "Позволяет получить список меток",
            responses = {
                @ApiResponse(responseCode = "200", description = "Список меток успешно получен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(arraySchema =
                                    @Schema(implementation = LabelResponseDto.class)))
                    )})
    @GetMapping
    public ResponseEntity<List<LabelResponseDto>> findAll() {
        List<LabelResponseDto> labels = labelService.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(labels.size()));

        return new ResponseEntity<>(labels, headers, HttpStatus.OK);
    }

    @Operation(summary = "Создание новой метки", description = "Позволяет создать новую метку",
            responses = {@ApiResponse(responseCode = "200", description = "Метка успешно создана",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LabelResponseDto.class)))
            })
    @PostMapping
    public ResponseEntity<LabelResponseDto> save(@RequestBody LabelRequestDto labelRequest) {
        LabelResponseDto responseDto = labelService.save(labelRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновление метки", description = "Позволяет обновить "
            + "информацию о метке",
            responses = {@ApiResponse(responseCode = "200", description = "Информация о метке "
                    + "успешно обновлена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LabelResponseDto.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<LabelResponseDto> updateById(@PathVariable Long id,
                                    @RequestBody LabelRequestDto labelRequest) {
        LabelResponseDto labelResponseDto = labelService.updateById(id, labelRequest);
        return new ResponseEntity<>(labelResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Удаление метки", description = "Позволяет удалить метку",
            responses = {@ApiResponse(responseCode = "200", description = "Метка успешно удалена",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        labelService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
