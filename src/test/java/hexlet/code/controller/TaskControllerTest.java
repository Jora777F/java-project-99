package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.task.TaskRequestDto;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    private Task testTask;

    @BeforeEach
    void beforeEach() {
        TaskStatus taskStatus = taskStatusRepository.findBySlug("draft").orElseThrow();
        testTask = Instancio.of(modelGenerator.getTaskModel())
                .set(Select.field(Task::getAssignee), null)
                .create();
        testTask.setTaskStatus(taskStatus);
        testTask.setLabels(Set.of());
        taskRepository.save(testTask);
    }

    @Test
    @DisplayName("Should return 204, when deleting task by id.")
    void deleteTaskById() throws Exception {
        mockMvc.perform(delete("/api/tasks/{id}", testTask.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status 200, when test find all tasks.")
    void findAllTasks() throws Exception {
        mockMvc.perform(get("/api/tasks")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status 200, when finding task by id.")
    public void findTaskById() throws Exception {
        mockMvc.perform(get("/api/tasks/{id}", testTask.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status 201, when save task.")
    void saveTask() throws Exception {
        TaskStatus taskStatus = taskStatusRepository.findBySlug("draft")
                .orElseGet(() -> {
                    TaskStatus status = new TaskStatus();
                    status.setName("Draft");
                    status.setSlug("draft");
                    return taskStatusRepository.save(status);
                });

        Label label = labelRepository.findByName("feature")
                .orElseGet(() -> {
                    Label newLabel = new Label();
                    newLabel.setName("feature");
                    return labelRepository.save(newLabel);
                });

        TaskRequestDto data = new TaskRequestDto();

        String name = "New Task Name";
        data.setTitle(name);
        data.setStatus(taskStatus.getSlug());
        data.setTaskLabelIds(Set.of(label.getId()));

        mockMvc.perform(post("/api/tasks")
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(data.getTitle()))
                .andDo(print());

        Optional<Task> task = taskRepository.findByName(name);
        assertNotNull(task.orElse(null), "Task not be null.");
    }

    @Test
    @DisplayName("Should return status 200, when updating task by id.")
    public void updateTaskById() throws Exception {
        var data = Instancio.of(modelGenerator.getTaskModel())
                .create();

        mockMvc.perform(put("/api/tasks/{id}", testTask.getId())
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
