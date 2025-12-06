package hexlet.code.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.task.status.TaskStatusResponse;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
class TaskStatusControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskStatusMapper taskStatusMapper;

    private TaskStatus testTaskStatus;

    @BeforeEach
    void beforeEach() {
        super.cleanup();
        testTaskStatus = Instancio.of(TaskStatus.class)
                .ignore(Select.field(TaskStatus.class, "id"))
                .create();
        taskStatusRepository.save(testTaskStatus);
    }

    @Test
    @DisplayName("Should return status code 200, when finding task status by id.")
    public void findTaskStatusById() throws Exception {
        mockMvc.perform(get("/api/task_statuses/{id}", testTaskStatus.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status code 200, when find all task status.")
    public void findAllTaskStatus() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/api/task_statuses")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String responseBody = response.getContentAsString();

        List<TaskStatusResponse> actualDto = objectMapper.readValue(responseBody, new TypeReference<>() { });

        List<TaskStatus> dbStatuses = taskStatusRepository.findAll();
        List<TaskStatusResponse> expectedDto = dbStatuses
                .stream()
                .map(taskStatusMapper::toDto)
                .toList();

        assertThat(actualDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Should return a status code 201, when saving task status.")
    public void saveTaskStatus() throws Exception {
        TaskStatus data = Instancio.of(TaskStatus.class)
                .ignore(Select.field(TaskStatus.class, "id"))
                .create();

        mockMvc.perform(post("/api/task_statuses")
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(data.getName()))
                .andDo(print());

        Optional<TaskStatus> taskStatus = taskStatusRepository.findBySlug(data.getSlug());
        assertNotNull(taskStatus.orElse(null), "Task status not be null.");
    }

    @Test
    @DisplayName("Should return status code 200, when updating task status by id.")
    public void updateTaskStatusById() throws Exception {
        var data = Instancio.of(TaskStatus.class)
                .ignore(Select.field(TaskStatus.class, "id"))
                .create();

        mockMvc.perform(put("/api/task_statuses/{id}", testTaskStatus.getId())
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(data.getName()))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status code 204, when deleting task status.")
    public void deleteTaskStatus() throws Exception {
        mockMvc.perform(delete("/api/task_statuses/{id}", testTaskStatus.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
