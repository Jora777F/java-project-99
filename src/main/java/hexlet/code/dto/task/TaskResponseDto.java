package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto implements Serializable {

    private Long id;
    private String title;
    private Long index;
    private String content;
    private String status;

    @JsonProperty("assignee_id")
    private Long assigneeId;
    //TODO: Добавить taskLabelIds
    private Instant createdAt;
}
