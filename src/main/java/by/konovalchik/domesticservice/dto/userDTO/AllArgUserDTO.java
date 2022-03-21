package by.konovalchik.domesticservice.dto.userDTO;

import by.konovalchik.domesticservice.entity.Role;
import by.konovalchik.domesticservice.entity.Telephone;
import by.konovalchik.domesticservice.entity.UserRating;
import by.konovalchik.domesticservice.utils.ConstraintsMessageManager;
import by.konovalchik.domesticservice.utils.Patterns;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AllArgUserDTO {


    private Role role;

    @NotBlank
    @Size(min = 3, max = 30, message = ConstraintsMessageManager.FIRST_NAME_ERROR)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 30, message = ConstraintsMessageManager.LAST_NAME_ERROR)
    private String lastName;

    @NotBlank
    @Pattern(regexp = Patterns.EMAIL, message = ConstraintsMessageManager.EMAIL_ERROR)
    private String email;

    @NotBlank
    @Size(min = 3, max = 30, message = ConstraintsMessageManager.NUMBER_USER_ERROR)
    private String telephone;

    @NotBlank
    @Size(min = 3, max = 500, message = ConstraintsMessageManager.PICTURE_ERROR)
    private String picture;

    @NotBlank
    @Size(min = 3, max = 30, message = ConstraintsMessageManager.USERNAME_ERROR)
    private String  username;

    @NotBlank
    @Size(min = 6, max = 500, message = ConstraintsMessageManager.PASSWORD_ERROR)
    @Pattern(regexp = Patterns.PASSWORD)
    private String password;



}
