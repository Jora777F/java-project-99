package hexlet.code.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UserUpdateDto {

    private String firstName;
    private String lastName;

    @Email(message = "Email должен быть валидным")
    private String email;

    @Size(min = 3, message = "Минимум 3 символа")
    private String password;
}
