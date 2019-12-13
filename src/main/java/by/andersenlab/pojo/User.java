package by.andersenlab.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {

    private Long id;
    private String password;
    private String login;
    private List<Order> orderList;

    public User(String password, String login) {
        this.password = password;
        this.login = login;
    }

    public User(Long id, String password, String login) {
        this.id = id;
        this.password = password;
        this.login = login;
    }
}
