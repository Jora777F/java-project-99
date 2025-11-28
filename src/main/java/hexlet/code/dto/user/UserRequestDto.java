package hexlet.code.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность запроса пользователя")
public final class UserRequestDto implements Serializable {

    @Schema(description = "Имя пользователя", example = "Максим")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "Электронный адрес пользователя", example = "hexlet@example.com")
    private String email;

    @Schema(description = "Пароль пользователя")
    private String password;
}
