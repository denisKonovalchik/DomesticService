package by.konovalchik.domesticservice.dto.taskDTO;

import by.konovalchik.domesticservice.entity.CategoryOfTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryTaskDTO {

    private  long id;

    private CategoryOfTask category;

}
