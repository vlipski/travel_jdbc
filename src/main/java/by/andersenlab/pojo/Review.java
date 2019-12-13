package by.andersenlab.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Review {

    private Long id;
    private String body;
    private Date date;
    private Long tourId;
    private Long userId;
}
