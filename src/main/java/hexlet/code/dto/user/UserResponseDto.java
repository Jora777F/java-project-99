package hexlet.code.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class UserResponseDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Instant createdAt;
}
