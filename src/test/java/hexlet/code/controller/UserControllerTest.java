package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.user.UserRequestDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        properties = {
                "spring.jpa.defer-datasource-initialization=false",
                "spring.sql.init.mode=never"
        }
)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelGenerator modelGenerator;

    private User testUser;

    @BeforeEach
    void beforeEach() {
        testUser = Instancio.of(modelGenerator.getUserModel())
                .create();
        userRepository.save(testUser);
    }

    @Test
    @DisplayName("Should return status ok, when test find all users.")
    void findAllTest() throws Exception {
        mockMvc.perform(get("/api/users")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status ok, when test delete user by id.")
    void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", testUser.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user(testUser.getEmail())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status ok, when test find user by id.")
    void findByIdTest() throws Exception {
        mockMvc.perform(get("/api/users/{id}", testUser.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status ok, when test update user by id.")
    public void updateByIdTest() throws Exception {
        UserRequestDto data = new UserRequestDto();
        data.setFirstName("New name");

        mockMvc.perform(put("/api/users/{id}", testUser.getId())
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(testUser.getEmail())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status ok, when test save user.")
    void saveTest() throws Exception {
        User data = Instancio.of(modelGenerator.getUserModel()).create();
        mockMvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
