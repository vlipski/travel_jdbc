package by.andersenlab.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
public class Tour {

    private Long id;
    private Date dateFrom;
    private Date dateTo;
    private String name;
    private Long orderId;
}
