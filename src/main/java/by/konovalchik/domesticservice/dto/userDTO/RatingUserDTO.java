package by.konovalchik.domesticservice.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RatingUserDTO {
    private long id;

    @Min(1)
    @Max(5)
    private int score;

}
