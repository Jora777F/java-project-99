package hexlet.code.controller;

import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseControllerTest {

    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    protected TaskStatusRepository taskStatusRepository;

    @Autowired
    protected LabelRepository labelRepository;

    @Autowired
    protected UserRepository userRepository;

    @BeforeEach
    void cleanup() {
        // Сначала удаляем задачи (они ссылаются на статусы, пользователей и метки)
        taskRepository.deleteAll();

        // Затем удаляем метки
        labelRepository.deleteAll();

        // Затем удаляем статусы
        taskStatusRepository.deleteAll();

        // В конце удаляем пользователей
        userRepository.deleteAll();
    }
}
