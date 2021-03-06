package by.konovalchik.domesticservice.dto.telephoneDTO;

import by.konovalchik.domesticservice.utils.ConstraintsMessageManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdNumberTelDTO {

        private long id;

        @NotBlank
        @Size(min = 9, max = 15,  message = ConstraintsMessageManager.NUMBER_USER_ERROR)
        private String telephone;


}
