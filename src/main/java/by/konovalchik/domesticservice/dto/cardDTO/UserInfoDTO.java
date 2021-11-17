package by.konovalchik.domesticservice.dto.cardDTO;


import by.konovalchik.domesticservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO {

    private User user;


}
