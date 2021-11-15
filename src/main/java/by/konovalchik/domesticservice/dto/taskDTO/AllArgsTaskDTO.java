package by.konovalchik.domesticservice.dto.taskDTO;
import by.konovalchik.domesticservice.entity.CategoryOfTask;
import by.konovalchik.domesticservice.entity.TaskStatus;
import by.konovalchik.domesticservice.utils.ConstraintsMessageManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllArgsTaskDTO {


    private long id;

    private CategoryOfTask category;

    private TaskStatus status;

    @NotBlank
    @Size(min = 3, max = 500, message = ConstraintsMessageManager.NOT_EMPTY_ERROR)
    private String name;

    @NotBlank
    @Size(min = 3, max = 500, message = ConstraintsMessageManager.NOT_EMPTY_ERROR)
    private String description;

    @NotBlank
    @Size(min = 3, max = 20, message = ConstraintsMessageManager.NAME_OF_CITY_ERROR)
    private String city;

    @NotBlank
    @Size(min = 3, max = 20, message = ConstraintsMessageManager.NAME_OF_STREET_ERROR)
    private String street;

    @NotBlank
    @Size(min = 1, max = 6, message = ConstraintsMessageManager.HOUSE_ERROR)
    private String house;

    @NotBlank
    @NotEmpty(message = ConstraintsMessageManager.NOT_EMPTY_ERROR)
    private String apartment;

    @NotEmpty
    @Size(min = 9, message = ConstraintsMessageManager.NUMBER_USER_ERROR)
    private String telephone;


    private double price;

    private boolean express;

}
