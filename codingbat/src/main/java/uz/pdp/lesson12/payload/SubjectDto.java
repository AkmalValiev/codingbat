package uz.pdp.lesson12.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

    @NotNull(message = "name bo'sh bo'lmasligi kerak!")
    private String name;

    @NotNull(message = "description bo'sh bo'lmasligi kerak!")
    private String description;

    @NotNull(message = "courseId bo'sh bo'lmasligi kerak!")
    private Integer courseId;

    private boolean done;

    private Integer rating;
}
