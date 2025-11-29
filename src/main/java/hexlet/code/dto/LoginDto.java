package hexlet.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @Schema(description = "Логин пользователя (email)", example = "hexlet@example.com")
    private String username;

    @Schema(description = "Пароль пользователя", example = "qwerty")
    private String password;
}
