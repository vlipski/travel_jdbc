package by.andersenlab.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Hotel {

    private Long id;
    private String name;
    private String address;
    private Long tourId;
}
