package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
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

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LabelControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Label testLabel;

    @BeforeEach
    public void beforeEach() {
        testLabel = Instancio.of(Label.class)
                .ignore(field(Label.class, "id"))
                .create();
        labelRepository.save(testLabel);
    }

    @Test
    @DisplayName("Should return status code 200, when finding label by id.")
    public void findLabelById() throws Exception {
        mockMvc.perform(get("/api/labels/{id}", testLabel.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status code 200, when finding all labels.")
    public void findAllLabels() throws Exception {
        mockMvc.perform(get("/api/labels")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status code 201, when save new label.")
    public void saveLabel() throws Exception {
        var data = Instancio.of(Label.class)
                .ignore(field(Label.class, "id"))
                .create();

        mockMvc.perform(post("/api/labels")
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isCreated())
                .andDo(print());

        Optional<Label> label = labelRepository.findByName(data.getName());
        assertNotNull(label.orElse(null));
    }

    @Test
    @DisplayName("Should return status code 200, when updating label by id.")
    public void updateLabelById() throws Exception {
        Label data = Instancio.of(Label.class)
                .ignore(field(Label.class, "id"))
                .create();

        mockMvc.perform(put("/api/labels/{id}", testLabel.getId())
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status code 204, when deleting label by id.")
    public void deleteLabelById() throws Exception {
        mockMvc.perform(delete("/api/labels/{id}", testLabel.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
