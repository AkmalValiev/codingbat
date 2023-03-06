package uz.pdp.lesson12.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotNull(message = "name bo'sh bo'lmasligi kerak!")
    private String name;
    @NotNull(message = "description bo'sh bo'lmasligi kerak!")
    private String description;
    private String codeDescription;
    private boolean done;
    @NotNull(message = "subjectId bo'sh bo'lmasligi kerak!")
    private Integer subjectId;

}
