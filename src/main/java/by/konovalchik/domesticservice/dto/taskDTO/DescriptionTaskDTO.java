package by.konovalchik.domesticservice.dto.taskDTO;


import by.konovalchik.domesticservice.utils.ConstraintsMessageManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class DescriptionTaskDTO {

    private long id;

    @NotBlank
    @Size(min = 3, max = 500, message = ConstraintsMessageManager.NOT_EMPTY_ERROR)
    private String name;

    @NotBlank
    @Size(min = 3, max = 500, message = ConstraintsMessageManager.NOT_EMPTY_ERROR)
    private String description;

}
