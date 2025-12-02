package hexlet.code.dto.label;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelRequestDto implements Serializable {
    @Size(min = 3, max = 1000)
    private String name;
}
