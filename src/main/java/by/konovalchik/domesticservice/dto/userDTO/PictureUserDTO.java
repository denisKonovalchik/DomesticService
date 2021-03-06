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
public class PictureUserDTO {

    private long id;

    @NotBlank
    @Size(min = 3, max = 500, message = ConstraintsMessageManager.PICTURE_ERROR)
    private String picture;


}

