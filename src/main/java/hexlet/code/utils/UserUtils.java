package hexlet.code.utils;

import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public boolean isOwner(Long userId, String email) {
        return userRepository.findById(userId)
                .map(user -> user.getEmail().equals(email))
                .orElse(false);
    }
}
