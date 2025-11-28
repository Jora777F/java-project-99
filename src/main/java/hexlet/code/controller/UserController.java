package hexlet.code.controller;

import hexlet.code.dto.user.UserRequestDto;
import hexlet.code.dto.user.UserResponseDto;
import hexlet.code.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получение пользователя по идентификатору",
            description = "Позволяет получить пользователя по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно получен",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                            content = @Content(schema = @Schema(implementation = String.class))),
            })
    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @Operation(summary = "Получение списка пользователей",
            description = "Позволяет получить список пользователей",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен",
                            content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = UserResponseDto.class)))
                    )})
    @GetMapping
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    @Operation(summary = "Создание пользователя", description = "Позволяет создать пользователя",
            responses = {@ApiResponse(responseCode = "200", description = "Пользователь успешно создан",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
            })
    @PostMapping
    public UserResponseDto save(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.save(userRequestDto);
    }

    @Operation(summary = "Обновление пользователя", description = "Позволяет обновить информацию о пользователе",
            responses = {@ApiResponse(responseCode = "200", description = "Информация о пользователе успешно обновлена.",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
            })
    @PutMapping("/{id}")
    public UserResponseDto updateById(@PathVariable Long id,
                                      @Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.updateById(id, userRequestDto);
    }

    @Operation(summary = "Удаление пользователя", description = "Позволяет удалить пользователя",
            responses = {@ApiResponse(responseCode = "200", description = "Пользователь успешно удален",
                    content = @Content(schema = @Schema(implementation = String.class)))
            })
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
