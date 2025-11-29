package hexlet.code.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность регистрации пользователя")
public final class UserRequestDto implements Serializable {

    @Schema(description = "Имя пользователя", example = "Максим")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "Электронный адрес пользователя", example = "hexlet@example.com")
    @Email(message = "Email должен быть валидным")
    @NotBlank(message = "Email обязателен")
    private String email;

    @Schema(description = "Пароль пользователя")
    @NotBlank(message = "Пароль обязателен")
    @Size(min = 3, message = "Пароль должен содержать минимум 3 символа")
    private String password;
}
