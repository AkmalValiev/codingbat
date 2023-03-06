package uz.pdp.lesson12.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    @NotNull(message = "name bo'sh bo'lmasligi kerak!")
    private String name;

}
