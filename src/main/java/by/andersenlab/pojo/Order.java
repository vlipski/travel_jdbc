package by.andersenlab.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    private Long id;
    private Date date;
    private Long userId;
    private List<Tour> tourList;

    public Order(Date date, Long userId) {
        this.date = date;
        this.userId = userId;
    }
}
