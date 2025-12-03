package hexlet.code.controller;

import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseControllerTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    protected TaskStatusRepository taskStatusRepository;

    @Autowired
    protected LabelRepository labelRepository;

    @Autowired
    protected UserRepository userRepository;

    @AfterEach
    void cleanup() {
        // Удаляем в правильном порядке (сначала зависимые, потом родительские)
        taskRepository.deleteAll();
        labelRepository.deleteAll();
        taskStatusRepository.deleteAll();
        userRepository.deleteAll();
    }
}
