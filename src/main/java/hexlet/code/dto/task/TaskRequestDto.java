package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto implements Serializable {
    private String title;
    private Long index;
    private String content;
    private String status;
    @JsonProperty("assignee_id")
    private Long assigneeId;

    //TODO: Добавить taskLabelIds
}
