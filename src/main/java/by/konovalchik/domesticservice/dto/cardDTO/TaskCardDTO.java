package by.konovalchik.domesticservice.dto.cardDTO;

import by.konovalchik.domesticservice.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCardDTO {

    private Task task;

    UserCardDTO userCardDTO;

    private String createdTime;


}
