package uz.pdp.lesson12.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "name bo'sh bo'lmasligi kerak!")
    private String mail;

    @NotNull(message = "password bo'sh bo'lmasligi kerak!")
    private String password;

    private List<Integer> coursesId;

}
