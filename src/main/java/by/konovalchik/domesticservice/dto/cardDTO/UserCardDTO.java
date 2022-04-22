package by.konovalchik.domesticservice.dto.cardDTO;
import by.konovalchik.domesticservice.entity.UserRating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCardDTO {

    private long id;

    private String  username;

    private UserRating rating;


}
