package ru.praktikum.model.user.auth;

import lombok.*;
import ru.praktikum.model.user.User;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserForAuthWithoutPassword {
    private String email;

    public UserForAuthWithoutPassword(User user) {
        this.email = user.getEmail();
    }
}
