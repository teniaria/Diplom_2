package ru.praktikum.model.user.auth;

import lombok.*;
import ru.praktikum.model.user.User;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserForAuthWithoutEmail {
    private String password;

    public UserForAuthWithoutEmail(User user) {
        this.password = user.getPassword();
    }
}
