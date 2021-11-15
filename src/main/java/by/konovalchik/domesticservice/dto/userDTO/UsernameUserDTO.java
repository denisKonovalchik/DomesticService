package by.konovalchik.domesticservice.dto.userDTO;


import by.konovalchik.domesticservice.utils.ConstraintsMessageManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsernameUserDTO {

    @NotBlank
    @Size(min = 3, max = 30, message = ConstraintsMessageManager.USERNAME_ERROR)
    private String  username;
}
