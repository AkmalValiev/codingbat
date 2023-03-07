package uz.pdp.lesson12.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

    @NotNull(message = "answerDescription bo'sh bo'lmasligi kerak")
    private String answerDescription;

    private boolean result;

    @NotNull(message = "userId bo'sh bo'lmasligi kerak")
    private Integer userId;

    @NotNull(message = "taskId bo'sh bo'lmasligi kerak")
    private Integer taskId;

}
