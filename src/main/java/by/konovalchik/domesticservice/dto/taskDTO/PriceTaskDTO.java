package by.konovalchik.domesticservice.dto.taskDTO;

import by.konovalchik.domesticservice.utils.ConstraintsMessageManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceTaskDTO {

    private long id;

    private double price;

}
