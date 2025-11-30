package hexlet.code.component;

import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("hexlet@example.com").isEmpty()) {
            User user = new User();
            user.setEmail("hexlet@example.com");
            user.setPassword(passwordEncoder.encode("qwerty"));
            userRepository.save(user);
            initDefaultStatuses();
        }
    }

    private void initDefaultStatuses() {
        Map<String, String> statuses = Map.of(
                "draft", "Draft",
                "to_review", "ToReview",
                "to_be_fixed", "ToBeFixed",
                "to_publish", "ToPublish",
                "published", "Published"
        );

        statuses.forEach((slug, name) -> {
            if (taskStatusRepository.findBySlug(slug).isEmpty()) {
                log.info("Creating status: {} ({})", slug, name);
                TaskStatus taskStatus = new TaskStatus();
                taskStatus.setName(name);
                taskStatus.setSlug(slug);
                taskStatus.setCreatedAt(Instant.now());
                taskStatusRepository.save(taskStatus);
            }
        });
        log.info("Total task status: {}", taskStatusRepository.count());
    }
}
