package by.konovalchik.domesticservice.dto.userDTO;

import by.konovalchik.domesticservice.utils.ConstraintsMessageManager;
import by.konovalchik.domesticservice.utils.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailUserDTO {

    @NotBlank
    @Pattern(regexp = Patterns.EMAIL, message = ConstraintsMessageManager.EMAIL_ERROR)
    private String email;


}
