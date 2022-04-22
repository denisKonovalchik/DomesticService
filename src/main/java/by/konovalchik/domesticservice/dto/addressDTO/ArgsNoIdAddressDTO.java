package by.konovalchik.domesticservice.dto.addressDTO;


import by.konovalchik.domesticservice.utils.ConstraintsMessageManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArgsNoIdAddressDTO {

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


}
