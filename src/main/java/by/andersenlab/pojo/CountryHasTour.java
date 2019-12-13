package by.andersenlab.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CountryHasTour {

    private Long countryId;
    private Long tourId;
}
